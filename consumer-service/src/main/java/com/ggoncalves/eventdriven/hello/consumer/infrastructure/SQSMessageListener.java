package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@ConditionalOnProperty(name = "messaging.provider", havingValue = "aws")
public class SQSMessageListener implements MessageListener {

//  private final AmazonSQS sqsClient;
//  private final String queueUrl = "TBD";

  // Constructor with dependencies

  @Override
  public void processMessage(HelloWorldEvent message) {
    log.info("Received Hello message via SQS: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }

  @PostConstruct
  public void startListening() {
    // Create a message poller that calls processMessage
  }
}