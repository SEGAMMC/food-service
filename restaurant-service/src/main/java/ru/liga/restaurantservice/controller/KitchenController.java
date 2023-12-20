package ru.liga.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.service.KitchenService;

import java.util.UUID;

/**
 * Контроллер для работы кухни с заказами
 */
@RestController
@RequestMapping("/api/v1/kitchens")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    /**
     * Обновление статуса заказа (uuid+JSON)
     *
     * @param uuid        - идентификационный номер заказа
     * @param orderStatus - новый статус заказа
     */
    @PutMapping("/order/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus) {
        kitchenService.updateOrderStatus(uuid, orderStatus);
    }

    /**
     * Изменение статуса заказа на KITCHEN_ACCEPTED
     *
     * @param uuid - идентификационный номер заказа
     */
    @PutMapping("/order/{uuid}/status/accept")
    public void updateOrderStatusAccept(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusAccept(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_PREPARING
     *
     * @param uuid - идентификационный номер заказа
     */
    @PutMapping("/order/{uuid}/status/prepair")
    public void updateOrderStatusPrepair(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusPrepair(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_DENIED
     *
     * @param uuid - идентификационный номер заказа
     */
    @PutMapping("/order/{uuid}/status/deny")
    public void updateOrderStatusDeny(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusDeny(uuid);
    }

    /**
     * Изменение статуса заказа на KITCHEN_REFUNDED
     *
     * @param uuid - идентификационный номер заказа
     */
    @PutMapping("/order/{uuid}/status/refund")
    public void updateOrderStatusRefund(@PathVariable UUID uuid) {
        kitchenService.updateOrderStatusRefund(uuid);
    }
}
