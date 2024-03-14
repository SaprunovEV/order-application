package by.sapra.orderstatusservice.kafka.model;

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
