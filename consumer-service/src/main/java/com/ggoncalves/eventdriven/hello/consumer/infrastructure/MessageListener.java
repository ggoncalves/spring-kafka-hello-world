package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

  private static final String HELLO_TOPIC = "hello-events";

  @KafkaListener(topics = HELLO_TOPIC, groupId = "#{kafkaConsumerConfig.groupId}")
  public void listen(HelloWorldEventMessage message) {
    log.info("Received Hello message: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }
}