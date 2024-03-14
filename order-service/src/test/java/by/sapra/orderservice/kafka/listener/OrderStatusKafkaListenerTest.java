package by.sapra.orderservice.kafka.listener;

import by.sapra.orderservice.kafka.model.StatusEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
class OrderStatusKafkaListenerTest {
    @Container
    static final KafkaContainer container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.3"));

    @DynamicPropertySource
    static void propertySourceRegistry(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);
    }

    @Value("${app.kafka.read-topic}")
    String orderTopic;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void whenSendToOrderTopic_thenReadByListener() throws Exception {
        StatusEvent statusEvent = new StatusEvent();
        statusEvent.setDate(Instant.now());
        statusEvent.setStatus("PROCESS");

        String key = "test-key";

        kafkaTemplate.send(orderTopic, key, statusEvent);

        await()
                .pollInterval(Duration.of(3, ChronoUnit.SECONDS))
                .atMost(Duration.of(10, ChronoUnit.SECONDS))
                .untilAsserted(() -> {});
    }

}