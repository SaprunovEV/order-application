package by.sapra.orderstatusservice.config;

import by.sapra.kafka.model.OrderEvent;
import by.sapra.kafka.model.StatusEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StatusEventFactory {
    public StatusEvent create(OrderEvent orderEvent) {
        StatusEvent statusEvent = new StatusEvent();
        statusEvent.setDate(Instant.now());
        statusEvent.setStatus("PROCESS");
        return statusEvent;
    }
}
