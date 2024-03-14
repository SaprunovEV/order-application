package by.sapra.orderstatusservice.kafka.model;

import java.time.Instant;

public class StatusEventFactory {
    public StatusEvent create(OrderEvent orderEvent) {
        StatusEvent statusEvent = new StatusEvent();
        statusEvent.setDate(Instant.now());
        statusEvent.setStatus("PROCESS");
        return statusEvent;
    }
}