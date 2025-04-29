package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {

  @Getter
  @Value(value = "${kafka.consumer.group-id:hello-consumer-group}")  // Default value
  private String groupId;

}