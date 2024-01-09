package ru.liga.restaurantservice.feign_core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.restaurantservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;


@FeignClient(name = "order-service", url = "http://localhost:8081/api/v1/orders")
public interface FeignToOrderService {

    /**
     * Получение списка позиций заказа для ресторана через OrderService
     *
     * @param uuid идентификационный номер заказа
     */
    @GetMapping("/{uuid}/items")
    List<OrderItemForRestaurantResponse> getOrderItemsByUuid(@PathVariable UUID uuid);

    /**
     * Изменение статуса заказа номеру UUID через OrderService
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @PutMapping("/{uuid}/status")
    void updateOrderStatus(@PathVariable UUID uuid,
                           @RequestBody OrderStatusRequest orderStatus);

    /**
     * Получение списка заказов для конкретного ресторана и статуса заказа
     *
     * @param restaurantId идентификационный номер ресторана
     * @param status       статус, по которому происходит фильтрация
     * @return список заказов имеющие нужный статус
     */
    @GetMapping
    List<OrderResponse> getOrderByRestaurantAndStatus(@RequestParam long restaurantId,
                                                      @RequestParam String status);

}

