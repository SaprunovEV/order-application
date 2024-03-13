package by.sapra.orderstatusservice.service;

import by.sapra.orderstatusservice.kafka.model.OrderEvent;
import by.sapra.orderstatusservice.kafka.model.StatusEvent;
import by.sapra.orderstatusservice.kafka.model.StatusEventFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderStatusService implements OrderStatusService {
    private final StatusEventFactory factory;
    private final KafkaTemplate<String, StatusEvent> kafkaTemplate;

    @Value("${app.kafka.order-status-topic}")
    private String topic;

    @Override
    public void processEvent(String key, OrderEvent orderEvent) {
        kafkaTemplate.send(topic, key, factory.create(orderEvent));
    }
}
