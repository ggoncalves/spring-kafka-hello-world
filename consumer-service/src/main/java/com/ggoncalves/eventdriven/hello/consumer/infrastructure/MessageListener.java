package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;

public interface MessageListener {

  void processMessage(HelloWorldEvent message);
}
