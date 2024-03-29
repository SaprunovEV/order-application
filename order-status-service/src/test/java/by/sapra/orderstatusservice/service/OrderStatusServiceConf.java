package by.sapra.orderstatusservice.service;

import by.sapra.orderstatusservice.config.StatusEventFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class OrderStatusServiceConf {
    @Bean
    public OrderStatusService service(StatusEventFactory factory, KafkaTemplate<String, Object> kafka) {
        return new KafkaOrderStatusService(factory, kafka);
    }
}
