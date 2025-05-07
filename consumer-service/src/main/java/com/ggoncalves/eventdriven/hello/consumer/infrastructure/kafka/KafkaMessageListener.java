package com.ggoncalves.eventdriven.hello.consumer.infrastructure.kafka;

import com.ggoncalves.eventdriven.hello.consumer.infrastructure.MessageListener;
import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@ConditionalOnProperty(name = "messaging.provider", havingValue = "kafka")
public class KafkaMessageListener implements MessageListener {

  /**
   * Listens for Kafka messages on the specified topic and logs the details
   * of the received HelloWorldEventMessage.
   *
   * @param message the HelloWorldEventMessage object containing the id, message content,
   *                and timestamp of the event
   * @see KafkaMessageConfig
   */
  @KafkaListener(
      topics = "${kafka.topics.hello}",
      groupId = "#{kafkaMessageConfig.groupId}",
      containerFactory = "helloWorldEventListenerContainerFactory"
  )
  @Override
  public void processMessage(HelloWorldEvent message) {
    log.info("Received Hello message: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }
}