package ru.liga.kitchenservice.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы кухни с заказами
 */
@RestController
@RequestMapping("")
public class KitchenController {
    /**
     * Обновление статуса заказа
     */
    @PutMapping("/")
    public void updateOrderStatusOnKitchen(){

    }
}
