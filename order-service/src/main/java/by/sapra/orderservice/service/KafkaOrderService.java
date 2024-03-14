package by.sapra.orderservice.service;

import by.sapra.orderservice.kafka.model.OrderEvent;
import by.sapra.orderservice.web.v1.model.OrderRequest;
import by.sapra.orderservice.web.v1.model.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaOrderService implements OrderService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${app.kafka.write-topic}")
    private String orderTopic;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        OrderEvent event = new OrderEvent();
        event.setProduct(request.getProduct());
        event.setQuantity(request.getQuantity());

        kafkaTemplate.send(orderTopic, UUID.randomUUID().toString(), event);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setMessage("order is accepted");
        return orderResponse;
    }
}
