package ru.liga.deliveryservice.service;

import ru.liga.deliveryservice.dto.OrderStatusRequest;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;

import java.util.UUID;

public interface DeliveryService {
    void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus);

    DeliveriesResponse getOrdersDeliveryByStatus(long courierId, String status);
}
