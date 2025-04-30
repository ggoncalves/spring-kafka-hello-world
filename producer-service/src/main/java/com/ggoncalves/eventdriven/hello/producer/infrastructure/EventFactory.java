package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;

import java.time.Instant;
import java.util.UUID;

public class EventFactory {

  public static HelloWorldEvent createHelloWorldMessage(String message) {
    return HelloWorldEvent.newBuilder()
        .setId(UUID.randomUUID().toString())
        .setMessage(message)
        .setTimestamp(Instant.now().toEpochMilli())
        .build();
  }
}