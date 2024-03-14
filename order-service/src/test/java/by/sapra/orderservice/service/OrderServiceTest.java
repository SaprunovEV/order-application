package by.sapra.orderservice.service;

import by.sapra.orderservice.kafka.model.OrderEvent;
import by.sapra.orderservice.web.v1.model.OrderRequest;
import by.sapra.orderservice.web.v1.model.OrderResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderServiceConf.class)
class OrderServiceTest {
    @DynamicPropertySource
    static void propertySource(DynamicPropertyRegistry registry) {
        registry.add("app.kafka.write-topic", () -> "test-topic");
    }

    @MockBean
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.write-topic}")
    String orderTopic;
    @Autowired
    OrderService service;

    @Test
    void whenCreateOrder_thenReturnNotNullValue() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setProduct("test_product");
        request.setQuantity(1);

        OrderResponse actual = service.createOrder(request);

        assertNotNull(actual);
    }

    @Test
    void whenCreateOrder_thenReturnMessageAboutOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setProduct("test_product");
        request.setQuantity(1);

        OrderResponse actual = service.createOrder(request);

        assertEquals("order is accepted",actual.getMessage());
    }

    @Test
    void whenCreateOrder_thenSendEventToKafka() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setProduct("test_product");
        request.setQuantity(1);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setQuantity(request.getQuantity());
        orderEvent.setProduct(request.getProduct());

        service.createOrder(request);

        verify(kafkaTemplate, times(1))
                .send(eq(orderTopic), anyString(), eq(orderEvent));
    }
}