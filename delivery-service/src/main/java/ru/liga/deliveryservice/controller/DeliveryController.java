package ru.liga.deliveryservice.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.request.OrderStatusRequest;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;
import ru.liga.deliveryservice.service.DeliveryService;


/**
 * Контроллер для работы с доставкой
 */
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * Получение списка доступных заказов для доставки с соответствующим
     * статусом заказа
     *
     * @param status статус заказа
     * @return возвращает список заказов на доставку с соответствиющим статусом
     */
    @GetMapping
    public ResponseEntity<DeliveriesResponse> getOrdersByStatus(
            @RequestParam(name = "courier") long courierId,
            @RequestParam(name = "status") String status) {
        return ResponseEntity.ok(deliveryService.getOrdersDeliveryByStatus(courierId,
                status));
    }

    /**
     * Обновление статуса заказа
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @PutMapping("/order/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid,
                                  @RequestBody OrderStatusRequest orderStatus) {
        deliveryService.updateOrderStatus(uuid, orderStatus);
    }

    /**
     * Подтверждение от курьера, что он доставит заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @PutMapping("/couriers/{courierId}/takeOrder/{uuid}")
    public void courierTakeOrder(@PathVariable long courierId,
                                 @PathVariable UUID uuid) {
        deliveryService.courierTakeOrder(courierId, uuid);
    }

    /**
     * Подтверждение от курьера, что он забрал заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @PutMapping("/couriers/{courierId}/pickupOrder/{uuid}")
    public void courierPickUPOrder(@PathVariable long courierId,
                                   @PathVariable UUID uuid) {
        deliveryService.courierPickUPOrder(courierId, uuid);
    }

    /**
     * Подтверждение от курьера, что он доставил заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @PutMapping("/couriers/{courierId}/completeOrder/{uuid}")
    public void courierCompleteOrder(@PathVariable long courierId,
                                     @PathVariable UUID uuid) {
        deliveryService.courierCompleteOrder(courierId, uuid);
    }
}
