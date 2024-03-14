package by.sapra.orderservice.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class OrderServiceConf {
    @Bean
    public OrderService service(KafkaTemplate<String, Object> kafka) {
        return new KafkaOrderService(kafka);
    }
}
