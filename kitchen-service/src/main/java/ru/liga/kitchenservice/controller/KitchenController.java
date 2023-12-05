package ru.liga.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.kitchenservice.dto.OrderStatusRequest;
import ru.liga.kitchenservice.service.KitchenService;

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
     * Обновление статуса заказа
     */
    @PutMapping("/order/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus){
        kitchenService.updateOrderStatus();
    }
}
