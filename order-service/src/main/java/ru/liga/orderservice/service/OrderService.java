package ru.liga.orderservice.service;

import ru.liga.orderservice.dto.request.NewOrderRequest;
import ru.liga.orderservice.dto.request.OrderStatusRequest;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse getOrderById(UUID uuid);

    List<OrderResponse> getAllOrders();

    OrderPaymentResponse createNewOrder(NewOrderRequest newOrder);

    void cancellOrder(UUID uuid);

    void updateOrderStatus(OrderStatusRequest orderStatus);
}
