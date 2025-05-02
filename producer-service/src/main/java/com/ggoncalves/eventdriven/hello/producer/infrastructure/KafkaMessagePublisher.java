package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class KafkaMessagePublisher implements MessagePublisher {

  private final KafkaTemplate<String, HelloWorldEvent> kafkaTemplate;

  @Override
  public void publishHelloMessage(HelloWorldEvent message) {
    log.info("Publishing message: {}", message);
    kafkaTemplate.send(KafkaConfig.HELLO_TOPIC, message.getId(), message);
  }
}