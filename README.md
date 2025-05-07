# Spring Kafka Hello World

A demonstrative project showcasing event-driven architecture using Kafka, SQS, and Spring Boot.

## Overview

This project implements a simple event-driven system with producer and consumer services that can communicate via Apache Kafka or Amazon SQS. It demonstrates several important concepts in modern distributed systems:

- Event-driven architecture
- Protocol Buffers (protobuf) serialization
- Spring Boot application design
- Multiple messaging providers with runtime selection
- Clean architecture principles with separation of concerns

## Architecture

The project consists of three main modules:

### ✨ Core Modules

| Module | Description |
|--------|-------------|
| <span style="color:blue">**producer-service**</span> | Publishes events to Kafka or SQS |
| <span style="color:green">**consumer-service**</span> | Consumes events from Kafka or SQS |
| **shared-domain** | Contains common domain models and interfaces |

> **Note:** The color-coding (blue for producer, green for consumer) is used throughout this document to help distinguish between these components.

### Messaging Providers

The application can switch between two messaging providers:

- **Apache Kafka**: An open-source distributed event streaming platform
- **Amazon SQS**: AWS's managed message queue service

The provider can be selected through configuration without changing the application code, demonstrating the Strategy pattern and dependency inversion.

### Communication Protocol

Events are serialized using Protocol Buffers (protobuf), a language-neutral, platform-neutral, extensible mechanism for serializing structured data.

## Project Structure

```
spring-kafka-hello-world/
├── shared-domain/                # Shared domain models and interfaces
│   ├── src/main/proto/           # Protocol buffer definitions
│   └── pom.xml                   # Shared domain module dependencies
├── producer-service/             # Service that produces events
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/             # Java source code
│   │   │   └── resources/        # Configuration files
│   │   └── test/                 # Test classes
│   └── pom.xml                   # Producer service dependencies
├── consumer-service/             # Service that consumes events
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/             # Java source code
│   │   │   └── resources/        # Configuration files
│   │   └── test/                 # Test classes
│   └── pom.xml                   # Consumer service dependencies
├── mvnw                          # Maven wrapper (Unix)
├── mvnw.cmd                      # Maven wrapper (Windows)
├── pom.xml                       # Root Maven configuration
└── kafka-docker-compose.yml      # Docker Compose for Kafka setup
```

## Technologies

- **Java 17+** - Core programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Kafka 3.1.0** - Kafka integration with Spring
- **Protocol Buffers 4.29.3** - Data serialization
- **Amazon SQS Java SDK** - AWS SQS integration
- **Docker** - Containerization for Kafka
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **AssertJ** - Fluent assertions
- **Log4j2** - Logging

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (or use included Maven wrapper)
- Docker and Docker Compose (for Kafka)
- AWS account with configured credentials (for SQS)

### Running Kafka Locally

1. Start Kafka using Docker Compose:

```bash
docker-compose -f kafka-docker-compose.yml up -d
```

This will start Kafka and Zookeeper containers on your local machine.

### Building the Project

Use the Maven wrapper to build all modules:

```bash
./mvnw clean install
```

Or on Windows:

```bash
mvnw.cmd clean install
```

## Configuration

### ⚠️ Selecting the Messaging Provider

You can choose between Kafka and AWS SQS by changing the `messaging.provider` property in each service's `application.properties`:

```properties
# Choose messaging provider: kafka or aws
messaging.provider=kafka
```

### <span style="color:blue">Producer Configuration</span>

The Kafka producer configuration in `producer-service/src/main/resources/application.properties`:

```properties
# Kafka producer configurations
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=com.ggoncalves.eventdriven.hello.producer.infrastructure.CustomProtobufSerializer
```

### <span style="color:green">Consumer Configuration</span>

The Kafka consumer configuration in `consumer-service/src/main/resources/application.properties`:

```properties
# Kafka consumer configurations
spring.kafka.bootstrap-servers=localhost:9092
kafka.topics.hello=hello-events
```

### AWS SQS Configuration

For SQS, ensure you have AWS credentials configured:

```properties
# AWS specific properties
aws.region=us-east-1
aws.sqs.queue-url=https://sqs.us-east-1.amazonaws.com/148387111490/hello-events-queue.fifo
aws.profile=HelloWorldSQSUser
```

## Running the Applications

### <span style="color:green">Start the Consumer Service</span>

Basic startup:
```bash
./mvnw spring-boot:run -pl consumer-service
```

With explicit messaging provider:
```bash
# Use Kafka
./mvnw spring-boot:run -pl consumer-service -Dspring-boot.run.arguments="--messaging.provider=kafka"

# Use AWS SQS
./mvnw spring-boot:run -pl consumer-service -Dspring-boot.run.arguments="--messaging.provider=aws"
```

With custom Kafka consumer group:
```bash
./mvnw spring-boot:run -pl consumer-service -Dspring-boot.run.arguments="--kafka.consumer.group-id=abcd"
```

### <span style="color:blue">Start the Producer Service</span>

Basic startup:
```bash
./mvnw spring-boot:run -pl producer-service
```

With explicit messaging provider:
```bash
# Use Kafka
./mvnw spring-boot:run -pl producer-service -Dspring-boot.run.arguments="--messaging.provider=kafka"

# Use AWS SQS
./mvnw spring-boot:run -pl producer-service -Dspring-boot.run.arguments="--messaging.provider=aws"
```

The producer service will start a command-line interface where you can type messages to be sent.

## Key Components

### <span style="color:blue">Producer Components</span>

| Component | Description |
|-----------|-------------|
| `MessagePublisher` | Interface defining the contract for sending messages |
| `KafkaMessagePublisher` | Implementation for Kafka |
| `SQSMessagePublisher` | Implementation for AWS SQS |
| `EventFactory` | Creates properly formatted events |
| `ProducerCommandLineLoopRunner` | CLI for sending messages |

### <span style="color:green">Consumer Components</span>

| Component | Description |
|-----------|-------------|
| `MessageListener` | Interface defining the contract for receiving messages |
| `KafkaMessageListener` | Implementation for Kafka |
| `SQSMessageListener` | Implementation for AWS SQS |
| `CustomProtobufDeserializer` | Deserializes protobuf messages |
| `MessagingConfiguration` | Configuration interface |

## Design Patterns and Principles

This project demonstrates several important patterns and principles:

1. **Strategy Pattern**: Dynamically selects the messaging provider at runtime
2. **Dependency Inversion**: Components depend on abstractions, not concrete implementations
3. **Interface Segregation**: Well-defined, focused interfaces
4. **Command Line Runner**: Spring Boot pattern for running command-line applications
5. **Factory Pattern**: Creating message objects with proper initialization

## Testing

The project includes unit tests for critical components. Run tests with:

```bash
./mvnw test
```

## Extensions and Future Work

Potential enhancements to this project could include:

1. Adding a REST API to the producer service
2. Implementing message validation
3. Adding monitoring and metrics
4. Adding a web UI for sending messages
5. Implementing error recovery and retry mechanisms

## Contributors

This project was created for educational purposes.

## License

This project is licensed under the Apache License 2.0.
