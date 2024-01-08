package ru.liga.orderservice.service;

import ru.liga.common.entity.Courier;
import ru.liga.common.entity.OrderInfo;
import ru.liga.orderservice.dto.request.AddOrderItemRequest;
import ru.liga.orderservice.dto.request.NewOrderRequest;
import ru.liga.orderservice.dto.request.OrderStatusRequest;
import ru.liga.orderservice.dto.request.UpdateOrderItemRequest;
import ru.liga.orderservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse getOrderByUuid(UUID uuid);

    List<OrderResponse> getOrdersByCustomerId(long id);

    OrderPaymentResponse createNewOrder(long id, NewOrderRequest newOrderRequest);

    void cancellOrder(UUID uuid);

    boolean updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus);

    boolean updateOrderStatusComplete(UUID uuid);

    void addNewOrderItem(UUID uuid, AddOrderItemRequest newOrderItem);

    void deleteOrderItem(UUID uuid, long id);

    void updateOrderItem(UUID uuid, long id, UpdateOrderItemRequest updateItem);

    List<OrderItemForRestaurantResponse> getOrderItemsByUuid(UUID uuid);

    OrderInfo getOrderInfoByUuidForCouriers(UUID uuid);

    List<OrderResponse> getOrderByRestaurantAndStatus(long restaurant, String status);

    void addCourierInOrder(UUID uuid, Courier courier);

}
