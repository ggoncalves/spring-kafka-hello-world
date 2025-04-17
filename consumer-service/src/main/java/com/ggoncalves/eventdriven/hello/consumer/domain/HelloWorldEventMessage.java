package com.ggoncalves.eventdriven.hello.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorldEventMessage {

  private String id;
  private String message;
  private LocalDateTime timestamp;

  public static HelloWorldEventMessage create(String message) {
    return new HelloWorldEventMessage(
        java.util.UUID.randomUUID().toString(),
        message,
        LocalDateTime.now()
    );
  }

}