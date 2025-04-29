package com.ggoncalves.eventdriven.hello.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// No need to add a @ComponentScan in here, because this classe is annotated with @SpringBootApplication, and it's
// located in the base root package dir already.
@SpringBootApplication
public class ConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}