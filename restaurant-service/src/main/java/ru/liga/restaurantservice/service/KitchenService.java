package ru.liga.restaurantservice.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.restaurantservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface KitchenService {
    void updateOrderStatusByBody(UUID uuid, OrderStatusRequest orderStatus);

    void updateOrderStatusAccept(UUID uuid);

    void updateOrderStatusPrepair(UUID uuid);

    void updateOrderStatusDeny(UUID uuid);

    void updateOrderStatusRefund(UUID uuid);

    void updateOrderStatusPick(UUID uuid);

    void updateOrderStatusPending(UUID uuid);

    List<OrderItemForRestaurantResponse> getOrderItemsByUuid(UUID uuid);

    List<OrderResponse> getOrderByRestaurantAndStatus(@RequestParam long restaurantId,
                                                      @RequestParam String status);
}
