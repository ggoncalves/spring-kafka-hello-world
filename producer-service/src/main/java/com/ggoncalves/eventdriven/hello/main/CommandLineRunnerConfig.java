package com.ggoncalves.eventdriven.hello.main;

import com.ggoncalves.eventdriven.hello.producer.domain.HelloWorldEventMessage;
import com.ggoncalves.eventdriven.hello.producer.infrastructure.MessagePublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class CommandLineRunnerConfig {

  @Bean
  public CommandLineRunner commandLineRunner(MessagePublisher messagePublisher) {
    return args -> {
      System.out.println("Producer service started. Type a message and press Enter to publish. Type 'exit' to quit.");

      Scanner scanner = new Scanner(System.in);
      while (true) {
        System.out.print("Enter message: ");
        String input = scanner.nextLine();

        if ("exit".equalsIgnoreCase(input)) {
          System.out.println("Exiting application");
          break;
        }

        HelloWorldEventMessage message = HelloWorldEventMessage.create(input);
        messagePublisher.publishHelloMessage(message);
        System.out.println("Published message: " + message);
      }

      scanner.close();
      System.exit(0);
    };
  }
}