package ru.liga.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с ресторанами
 */
@RestController
@RequestMapping("/")
public class RestaurantController {


    /**
     * Получение информации о ресторане по его номеру
     */
    @GetMapping("/")
    public void getRestaurantById(){

    }

    /**
     * Изменение информации о ресторане
     */
    @PutMapping("/")
    public void updateRestaurant(){

    }

    /**
     * Изменение статуса ресторана
     */
    @PutMapping("/")
    public void updateRestaurantStatus(){

    }


}
