package ru.liga.customerservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для расширенной работы с клиентами с правами администратора
 */

@RestController
@RequestMapping("/")
public class CustomerControllerAdmin {

    /**
     * Блокировка клиента по ID с правами администратора
     */
    @PutMapping
    public void blockCourierByIdForAdmin() {

    }

    /**
     * Разблокировка клиента по ID с правами администратора
     */
    @PutMapping
    public void unblockCourierByIdForAdmin() {

    }

    /**
     * Изменение информации о курьере с правами администратора
     */
    @PutMapping
    public void updateCourierForAdmin() {

    }


}
