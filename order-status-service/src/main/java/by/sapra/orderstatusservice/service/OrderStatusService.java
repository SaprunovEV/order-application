package by.sapra.orderstatusservice.service;

import by.sapra.orderstatusservice.kafka.model.OrderEvent;

public interface OrderStatusService {

    void processEvent(String key, OrderEvent orderEvent);
}
