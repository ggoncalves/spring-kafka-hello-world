package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.ggoncalves.eventdriven.hello.producer.domain.HelloWorldEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePublisher {

  private final KafkaTemplate<String, HelloWorldEventMessage> kafkaTemplate;

  public void publishHelloMessage(HelloWorldEventMessage message) {
    System.out.println("Publishing message: " + message);
    kafkaTemplate.send(KafkaConfig.HELLO_TOPIC, message.getId(), message);
  }
}