package ru.liga.restaurantservice.service;

import ru.liga.restaurantservice.dto.request.OrderStatusRequest;

import java.util.UUID;

public interface KitchenService {
    void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus);

    void updateOrderStatusAccept(UUID uuid);

    void updateOrderStatusPrepair(UUID uuid);

    void updateOrderStatusDeny(UUID uuid);

    void updateOrderStatusRefund(UUID uuid);
}
