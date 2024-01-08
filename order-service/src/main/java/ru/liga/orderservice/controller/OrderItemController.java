package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.request.AddOrderItemRequest;
import ru.liga.orderservice.dto.request.UpdateOrderItemRequest;
import ru.liga.orderservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.orderservice.service.OrderService;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с позициями в заказе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderItemController {

    private final OrderService orderService;

    /**
     * Добавление новой позиции в заказ
     *
     * @param uuid         идентификационный номер заказа
     * @param newOrderItem информация о новой позиции
     */
    @PostMapping("/{uuid}/items")
    public void addNewOrderItem(@PathVariable UUID uuid
            , @RequestBody AddOrderItemRequest newOrderItem) {
        orderService.addNewOrderItem(uuid, newOrderItem);
    }

    /**
     * Удаление позиции из заказа
     *
     * @param uuid        идентификационный номер заказа
     * @param orderItemId номер позиции в заказе
     */
    @DeleteMapping("/{uuid}/items/{orderItemId}")
    public void deleteOrderItem(@PathVariable UUID uuid
            , @PathVariable long orderItemId) {
        orderService.deleteOrderItem(uuid, orderItemId);
    }

    /**
     * Изменение количества блюд в позиции заказа
     *
     * @param uuid            идентификационный номер заказа
     * @param orderItemId     номер позиции в заказе
     * @param updateOrderItem изменение количества данной позиции в заказ
     */
    @PutMapping("/{uuid}/items/{orderItemId}")
    public void updateOrderItem(@PathVariable UUID uuid
            , @PathVariable long orderItemId
            , @RequestBody UpdateOrderItemRequest updateOrderItem) {
        orderService.updateOrderItem(uuid, orderItemId, updateOrderItem);
    }

    //FEIGN methods

    /**
     * Получение списка позиций заказа для ресторана
     *
     * @param uuid идентификационный номер заказа
     */
    @GetMapping("/{uuid}/items")
    public List<OrderItemForRestaurantResponse> getOrderItemsByUuid(@PathVariable UUID uuid) {
        return orderService.getOrderItemsByUuid(uuid);
    }


}