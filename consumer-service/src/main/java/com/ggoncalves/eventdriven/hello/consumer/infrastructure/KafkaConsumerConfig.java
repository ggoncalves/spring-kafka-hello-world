package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KafkaConsumerConfig {

  // Must be set using environment variable `--kafka.consumer.group-id`
  // eg.: mvn spring-boot:run -Dspring-boot.run.arguments="--kafka.consumer.group-id=<group_id_name>"
  //
  // Default Value is `hello-consumer-group`
  @Value(value = "${kafka.consumer.group-id:hello-consumer-group}")
  private String groupId;

}