package ru.liga.customerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с клиентами
 */
@RestController
@RequestMapping("/")
public class CustomerController {

    /**
     * Получение информации о клиенте по ID
     */
    @GetMapping("")
    public void getCustomerById(){

    }

    /**
     * Изменение информации о клиенте
     */
    @PutMapping("")
    public void updateCustomer(){

    }
}
