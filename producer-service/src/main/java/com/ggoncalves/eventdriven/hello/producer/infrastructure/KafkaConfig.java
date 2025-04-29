package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  public static final String HELLO_TOPIC = "hello-events";

  @Bean
  public NewTopic helloTopic() {
    return TopicBuilder.name(HELLO_TOPIC)
        .partitions(3)
        .replicas(1)
        .build();
  }
}