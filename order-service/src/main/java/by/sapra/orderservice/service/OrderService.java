package by.sapra.orderservice.service;

import by.sapra.orderservice.web.v1.model.OrderRequest;
import by.sapra.orderservice.web.v1.model.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
}
