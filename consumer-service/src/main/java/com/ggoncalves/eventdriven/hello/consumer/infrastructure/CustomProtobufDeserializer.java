package com.ggoncalves.eventdriven.hello.consumer.infrastructure;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@AllArgsConstructor
public class CustomProtobufDeserializer<T extends Message> implements Deserializer<T> {

  private final Parser<T> parser;

  @Override
  public T deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return parser.parseFrom(data);
    } catch (InvalidProtocolBufferException e) {
      throw new SerializationException("Error deserializing Protobuf message", e);
    }
  }
}