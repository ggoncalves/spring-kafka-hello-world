package com.ggoncalves.eventdriven.hello.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProducerApplication should")
class ProducerApplicationTest {

  @Test
  @DisplayName("should bootstrap the application correctly")
  void shouldBootstrapApplicationCorrectly() {
    // Given
    String[] args = {"arg1", "arg2"};

    // Using Mockito's MockedStatic feature (available since Mockito 3.4.0)
    try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
      // When
      ProducerApplication.main(args);

      // Then
      springApplicationMock.verify(() ->
              SpringApplication.run(eq(ProducerApplication.class), eq(args)),
          Mockito.times(1));
    }
  }
}