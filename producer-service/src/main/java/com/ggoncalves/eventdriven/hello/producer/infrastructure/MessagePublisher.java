package com.ggoncalves.eventdriven.hello.producer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;

public interface MessagePublisher {
  void publishHelloMessage(HelloWorldEvent message);
}
