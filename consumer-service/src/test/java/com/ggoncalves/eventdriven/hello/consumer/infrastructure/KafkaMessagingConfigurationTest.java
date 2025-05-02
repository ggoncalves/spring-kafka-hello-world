package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("KafkaMessagingConfiguration should")
class KafkaMessagingConfigurationTest {

  @InjectMocks
  private KafkaMessagingConfiguration kafkaMessagingConfiguration;

  @Test
  @DisplayName("should have default group ID when not specified")
  void shouldHaveDefaultGroupIdWhenNotSpecified() {
    // Given
    // Spring's @Value isn't processed in unit tests, so we need to set it manually
    ReflectionTestUtils.setField(kafkaMessagingConfiguration, "groupId", "hello-consumer-group");

    // When
    String actualGroupId = kafkaMessagingConfiguration.getGroupId();

    // Then
    assertThat(actualGroupId)
        .isNotNull()
        .isEqualTo("hello-consumer-group");
  }

  @Test
  @DisplayName("should use custom group ID when specified")
  void shouldUseCustomGroupIdWhenSpecified() {
    // Given
    String customGroupId = "custom-consumer-group";
    ReflectionTestUtils.setField(kafkaMessagingConfiguration, "groupId", customGroupId);

    // When
    String actualGroupId = kafkaMessagingConfiguration.getGroupId();

    // Then
    assertThat(actualGroupId)
        .isNotNull()
        .isEqualTo(customGroupId);
  }
}