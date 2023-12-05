package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.OrderStatusRequest;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.UUID;

/**
 * Контроллер для работы с заказами на доставку
 */
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    /**
     * Обновление статуса заказа
     */
    @PutMapping("/order/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus){
        deliveryService.updateOrderStatus();
    }
}
