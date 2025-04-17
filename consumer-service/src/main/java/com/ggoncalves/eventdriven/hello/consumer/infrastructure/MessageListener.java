package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.consumer.domain.HelloWorldEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

  private static final String HELLO_TOPIC = "hello-events";
  private static final String GROUP_ID = "hello-consumer-group";

  @KafkaListener(topics = HELLO_TOPIC, groupId = GROUP_ID)
  public void listen(HelloWorldEventMessage message) {
    log.info("Received Hello message: id={}, content='{}', timestamp={}",
        message.getId(), message.getMessage(), message.getTimestamp());
  }
}