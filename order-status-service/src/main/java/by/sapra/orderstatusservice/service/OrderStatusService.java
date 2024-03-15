package by.sapra.orderstatusservice.service;

import by.sapra.kafka.model.OrderEvent;

public interface OrderStatusService {

    void processEvent(String key, OrderEvent orderEvent);
}
