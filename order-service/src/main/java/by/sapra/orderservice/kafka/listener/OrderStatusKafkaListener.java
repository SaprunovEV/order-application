package by.sapra.orderservice.kafka.listener;

import by.sapra.orderservice.kafka.model.StatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class OrderStatusKafkaListener {
    @KafkaListener(
            topics = "${app.kafka.read-topic}",
            groupId = "${app.kafka.group-id}",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void handleStatusEvent(
            @Payload StatusEvent event,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Instant timestamp
    ){
        log.info("Received message: {}", event);
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
