package ru.liga.orderservice.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с заказами
 */
@RestController
@RequestMapping("/")
public class OrderController {

    /**
     *  Создание нового заказа
     */
    @PostMapping("/")
    public void createNewOrder(){

    }

    /**
     * Получение всех заказов клиента
     */
    @GetMapping("/")
    public void getAllOrders(){

    }

    /**
     * Получение информации заказа по его номеру
     */
    @GetMapping("/")
    public void getOrderById(){

    }

    /**
     * Изменение информации в заказе
     */
    @PutMapping("/")
    public void updateOrder(){

    }

    /**
     * Отмена заказа по номеру ID
     */
    @DeleteMapping("/")
    public void cancellOrder(){

    }

    /**
     * Изменение статуса заказа номеру ID
     */
    @DeleteMapping("/")
    public void updateOrderStatus(){

    }


}
