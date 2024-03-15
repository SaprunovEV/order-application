package by.sapra.orderstatusservice.service;

import by.sapra.kafka.model.OrderEvent;
import by.sapra.kafka.model.StatusEvent;
import by.sapra.orderstatusservice.config.StatusEventFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderStatusServiceConf.class)
class OrderStatusServiceTest {

    @DynamicPropertySource
    public static void propertySource(DynamicPropertyRegistry registry) {
        registry.add("app.kafka.write-topic", () -> "order-status-topic");
    }

    @Autowired
    OrderStatusService service;

    @MockBean
    KafkaTemplate<String, Object> kafkaTemplate;
    @MockBean
    StatusEventFactory factory;

    @Value("${app.kafka.write-topic}")
    String orderStatusTopic;

    @Test
    void whenProcessEvent_thenSendStatusEventToKafka() throws Exception {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setProduct("test-product");
        orderEvent.setQuantity(20);

        String key = "test-key";

        StatusEvent statusEvent = new StatusEvent();
        statusEvent.setStatus("TEST_STATUS");
        statusEvent.setDate(Instant.now());

        when(factory.create(orderEvent)).thenReturn(statusEvent);

        service.processEvent(key, orderEvent);

        verify(kafkaTemplate, times(1)).send(orderStatusTopic, key, statusEvent);
    }
}