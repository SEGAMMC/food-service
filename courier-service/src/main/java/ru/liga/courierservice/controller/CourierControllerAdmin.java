package ru.liga.courierservice.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для расширенной работы с курьерами с правами администратора
 */

@RestController
@RequestMapping("/")
public class CourierControllerAdmin {

    /**
     * Блокировка курьера по ID с правами администратора
     */
    @DeleteMapping
    public void blockCourierByIdForAdmin() {

    }

    /**
     * Изменение информации о курьере по ID с правами администратора
     */
    @PutMapping
    public void updateCourierByIdForAdmin() {

    }


}
