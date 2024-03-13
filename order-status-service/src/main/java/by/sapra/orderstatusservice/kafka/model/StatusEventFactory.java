package by.sapra.orderstatusservice.kafka.model;

public class StatusEventFactory {
    public StatusEvent create(OrderEvent orderEvent) {
        return new StatusEvent();
    }
}
