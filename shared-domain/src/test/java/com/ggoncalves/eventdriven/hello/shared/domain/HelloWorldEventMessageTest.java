package com.ggoncalves.eventdriven.hello.shared.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("HelloWorldEventMessage should")
class HelloWorldEventMessageTest {

  @Test
  @DisplayName("should create a new message with generated ID and current timestamp")
  void shouldCreateNewMessageWithGeneratedIdAndCurrentTimestamp() {
    // Given
    String messageContent = "Hello, World!";
    LocalDateTime beforeCreation = LocalDateTime.now();

    // When
    HelloWorldEventMessage message = HelloWorldEventMessage.create(messageContent);
    LocalDateTime afterCreation = LocalDateTime.now();

    // Then
    assertThat(message.getMessage()).isEqualTo(messageContent);
    assertThat(message.getId()).isNotNull().isNotEmpty();
    assertThat(message.getTimestamp())
        .isNotNull()
        .isBetween(beforeCreation, afterCreation.plusSeconds(1));
  }

  @Test
  @DisplayName("should set and get all properties correctly")
  void shouldSetAndGetAllPropertiesCorrectly() {
    // Given
    String id = "test-id-123";
    String message = "Test message";
    LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    // When
    HelloWorldEventMessage eventMessage = new HelloWorldEventMessage();
    eventMessage.setId(id);
    eventMessage.setMessage(message);
    eventMessage.setTimestamp(timestamp);

    // Then
    assertThat(eventMessage.getId()).isEqualTo(id);
    assertThat(eventMessage.getMessage()).isEqualTo(message);
    assertThat(eventMessage.getTimestamp()).isEqualTo(timestamp);
  }

  @Test
  @DisplayName("should create message with all properties via constructor")
  void shouldCreateMessageWithAllPropertiesViaConstructor() {
    // Given
    String id = "constructor-id";
    String message = "Constructor message";
    LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    // When
    HelloWorldEventMessage eventMessage = new HelloWorldEventMessage(id, message, timestamp);

    // Then
    assertThat(eventMessage.getId()).isEqualTo(id);
    assertThat(eventMessage.getMessage()).isEqualTo(message);
    assertThat(eventMessage.getTimestamp()).isEqualTo(timestamp);
  }
}