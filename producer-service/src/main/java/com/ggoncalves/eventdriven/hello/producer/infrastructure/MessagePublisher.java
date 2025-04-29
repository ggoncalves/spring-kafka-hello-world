package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisher {

  private final KafkaTemplate<String, HelloWorldEventMessage> kafkaTemplate;

  public void publishHelloMessage(HelloWorldEventMessage message) {
    log.info("Publishing message: {}", message);
    kafkaTemplate.send(KafkaConfig.HELLO_TOPIC, message.getId(), message);
  }
}