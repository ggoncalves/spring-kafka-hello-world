package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEventMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessageListener {

  private static final String HELLO_TOPIC = "hello-events";

  /**
   * Listens for Kafka messages on the specified topic and logs the details
   * of the received HelloWorldEventMessage.
   *
   * @see com.ggoncalves.eventdriven.hello.consumer.infrastructure.KafkaConsumerConfig
   *
   * @param message the HelloWorldEventMessage object containing the id, message content,
   *                and timestamp of the event
   */
  @KafkaListener(topics = HELLO_TOPIC, groupId = "#{kafkaConsumerConfig.groupId}")
  public void listen(HelloWorldEventMessage message) {
    log.info("Received Hello message: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }
}