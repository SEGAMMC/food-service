package ru.liga.restaurantservice.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.restaurantservice.dto.response.OrderResponse;
import ru.liga.restaurantservice.service.KitchenService;

/**
 * Контроллер для работы кухни с заказами
 */
@RestController
@RequestMapping("/api/v1/kitchens")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    //Feign-methods

    /**
     * Получение списка позиций заказа для ресторана через OrderService
     *
     * @param uuid идентификационный номер заказа
     */
    @GetMapping("/orders/{uuid}")
    public ResponseEntity<List<OrderItemForRestaurantResponse>> getOrderItemsByUuid(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(kitchenService.getOrderItemsByUuid(uuid));
    }

    //Feign-methods

    /**
     * Получение списка заказов для конкретного ресторана и статуса заказа
     *
     * @param restaurantId идентификационный номер ресторана
     * @param status       статус, по которому происходит фильтрация
     * @return список заказов имеющие нужный статус
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrderByRestaurantAndStatus(
            @RequestParam long restaurantId,
            @RequestParam String status) {
        return ResponseEntity.ok(kitchenService
                .getOrderByRestaurantAndStatus(restaurantId, status));
    }


    /**
     * Обновление статуса заказа (uuid+JSON)
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @PutMapping("/orders/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid,
                                  @RequestBody OrderStatusRequest orderStatus) {
        kitchenService.updateOrderStatusByBody(uuid, orderStatus);
    }

    /**
     * Изменение статуса заказа на KITCHEN_ACCEPTED
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/accept")
    public void updateOrderStatusAccept(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusAccept(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_PREPARING
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/prepair")
    public void updateOrderStatusPrepair(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusPrepair(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_DENIED
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/deny")
    public void updateOrderStatusDeny(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusDeny(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_REFUNDED
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/refund")
    public void updateOrderStatusRefund(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusRefund(uuid);
    }

    /**
     * Заказ собирается, изменяет статус заказа на DELIVERY_PICKING
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/pick")
    public void updateOrderStatusPick(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusPick(uuid);
    }

    /**
     * Заказ готов, изменяет статус заказа на DELIVERY_PENDING
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/pending")
    public void updateOrderStatusPending(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusPending(uuid);
    }

    /**
     * Курьер забрал заказ, изменяет статус заказа на DELIVERY_DELIVERING
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/delivery")
    public void updateOrderStatusDelivery(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusDelivery(uuid);
    }
}
