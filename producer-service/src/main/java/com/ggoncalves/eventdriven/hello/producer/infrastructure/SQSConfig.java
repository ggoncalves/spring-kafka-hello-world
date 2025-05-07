package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "messaging.provider", havingValue = "aws")
public class SQSConfig {

  @Value("${aws.region}")
  private String region;

  @Value("${aws.sqs.queue-url}")
  private String queueUrl;

  @Value("${aws.profile}")
  private String profile;

  @Bean
  public AmazonSQS sqsClient() {
    return AmazonSQSClientBuilder.standard()
        .withRegion(Regions.fromName(region))
        .withCredentials(new ProfileCredentialsProvider(profile))
        .build();
  }

  @Bean
  public String queueUrl() {
    return queueUrl;
  }
}