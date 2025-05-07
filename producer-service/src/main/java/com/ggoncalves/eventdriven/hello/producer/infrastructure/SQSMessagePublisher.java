package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "messaging.provider", havingValue = "aws")
public class SQSMessagePublisher implements MessagePublisher {

  private final AmazonSQS sqsClient;
  private final String queueUrl;

  @Override
  public void publishHelloMessage(HelloWorldEvent message) {
    try {
      log.info("Publishing message to SQS: {}", message);
      String jsonMessage = JsonFormat.printer().print(message);
      SendMessageRequest sendMessageRequest = new SendMessageRequest()
          .withQueueUrl(queueUrl)
          .withMessageBody(jsonMessage)
          .withMessageGroupId("hello-group") // Required for FIFO queues
          .withMessageDeduplicationId(message.getId()); // Required for FIFO queues

      sqsClient.sendMessage(sendMessageRequest);
    } catch (Exception e) {
      log.error("Error publishing to SQS", e);
      throw new RuntimeException("Failed to publish message to SQS", e);
    }
  }
}