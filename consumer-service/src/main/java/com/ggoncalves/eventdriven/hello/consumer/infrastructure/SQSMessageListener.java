package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "messaging.provider", havingValue = "aws")
public class SQSMessageListener implements MessageListener {

  private final AmazonSQS sqsClient;
  private final String queueUrl;

  @Override
  public void processMessage(HelloWorldEvent message) {
    // This method is required by the interface but will be
    // called directly from the polling method after deserialization
    log.info("Received Hello message from SQS: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }

  // This method performs the actual polling of the SQS queue
  @Scheduled(fixedDelay = 1000) // Poll every second
  public void pollMessages() {
    ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest()
        .withQueueUrl(queueUrl)
        .withMaxNumberOfMessages(10)
        .withWaitTimeSeconds(10); // Long polling

    List<Message> messages = sqsClient.receiveMessage(receiveRequest).getMessages();

    for (Message message : messages) {
      try {

        String messageBody = message.getBody();

        // Try to detect if this is a valid JSON object
        if (!messageBody.startsWith("{") || !messageBody.endsWith("}")) {
          log.warn("Received non-JSON message: '{}'. Skipping processing.", messageBody);
          // Delete invalid message to prevent it from being processed repeatedly
          sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
          continue;
        }

        // Parse the JSON message into a HelloWorldEvent
        HelloWorldEvent.Builder builder = HelloWorldEvent.newBuilder();
        JsonFormat.parser().merge(message.getBody(), builder);
        HelloWorldEvent event = builder.build();

        // Process the message using the interface method
        processMessage(event);

        // Delete the message from the queue after processing
        sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
      } catch (Exception e) {
        log.error("Error processing SQS message", e);
      }
    }
  }
}