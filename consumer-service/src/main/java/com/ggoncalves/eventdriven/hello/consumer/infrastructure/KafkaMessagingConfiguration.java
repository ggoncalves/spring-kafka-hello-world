package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@ConditionalOnProperty(name = "messaging.provider", havingValue = "kafka")
public class KafkaMessagingConfiguration implements MessagingConfiguration {

  @Value("${kafka.topics.hello}")
  private String topicName;

  // Must be set using environment variable `--kafka.consumer.group-id`
  // e.g.: mvn spring-boot:run -Dspring-boot.run.arguments="--kafka.consumer.group-id=<group_id_name>"
  //
  // Default Value is `hello-consumer-group`
  @Value(value = "${kafka.consumer.group-id:hello-consumer-group}")
  private String groupId;

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  public <T extends Message> ConsumerFactory<String, T> consumerFactory(Parser<T> parser) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, CustomProtobufDeserializer.class.getName());

    return new DefaultKafkaConsumerFactory<>(
        props,
        new ErrorHandlingDeserializer<>(new StringDeserializer()),
        new ErrorHandlingDeserializer<>(new CustomProtobufDeserializer<>(parser))
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, HelloWorldEvent> helloWorldEventListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, HelloWorldEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory(HelloWorldEvent.parser()));
    factory.setCommonErrorHandler(errorHandler());
    return factory;
  }

  @Bean
  public CommonErrorHandler errorHandler() {
    // Retry 3 times, with 1 second between retries
    return new DefaultErrorHandler(new FixedBackOff(1000L, 3L));
  }

}
