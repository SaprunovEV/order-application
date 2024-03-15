package by.sapra.orderstatusservice.listener;

import by.sapra.kafka.model.OrderEvent;
import by.sapra.orderstatusservice.service.OrderStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaOrderListener {
    private final OrderStatusService service;

    public KafkaOrderListener(OrderStatusService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${app.kafka.read-topic}",
            groupId = "${app.kafka.group-id}",
            containerFactory = "concurrentKafkaListenerContainerFactory"
    )
    public void handleOrderEvent(
            @Payload OrderEvent orderEvent,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key
    ) {
        log.info("Event: {}, Key: {}", orderEvent, key);
        service.processEvent(key, orderEvent);
    }
}
