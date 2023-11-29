package ru.liga.courierservice.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с курьерами
 */

@RestController
@RequestMapping("/")
public class CourierController {

    /**
     * Получение информации о курьере по ID
     */
    @GetMapping("")
    public void getCourierById() {

    }

    /**
     * Изменение информации о курьере по ID
     */
    @PutMapping("")
    public void updateCourier() {

    }

}
