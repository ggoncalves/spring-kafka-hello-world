package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.ggoncalves.eventdriven.hello.consumer.infrastructure.kafka.KafkaMessageListener;
import com.ggoncalves.eventdriven.hello.shared.domain.HelloWorldEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("KafkaMessageConfig should")
class KafkaMessageListenerTest {

  private ListAppender<ILoggingEvent> listAppender;
  private Logger logger;
  private KafkaMessageListener listener;

  @BeforeEach
  public void setUp() {
    listAppender = new ListAppender<>();
    listAppender.start();

    // Add appender to the logger of the class being tested
    logger = (Logger) LoggerFactory.getLogger(KafkaMessageListener.class);
    logger.addAppender(listAppender);
    logger.setLevel(Level.INFO);

    listener = new KafkaMessageListener();
  }

  @AfterEach
  public void tearDown() {
    logger.detachAppender(listAppender);
  }

  @Test
  @DisplayName("should log the received message")
  public void shouldLogReceivedMessage() {
    // Given
    HelloWorldEvent event = HelloWorldEvent.newBuilder()
        .setId("test-id")
        .setMessage("Test message")
        .setTimestamp(1234567890L)
        .build();

    // When
    listener.processMessage(event);

    // Then
    verifyLogMessage();
  }

  private void verifyLogMessage() {
    List<ILoggingEvent> events = listAppender.list;
    assertThat(events).hasSize(1);
    ILoggingEvent logEvent = events.get(0);

    assertThat(logEvent.getFormattedMessage())
        .contains("Received Hello message")
        .contains("id=test-id")
        .contains("content='Test message'")
        .contains("timestamp=1234567890");
  }
}