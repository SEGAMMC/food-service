package ru.liga.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.request.RestaurantStatusRequest;
import ru.liga.orderservice.dto.request.RestaurantUpdateInfoRequest;

/**
 * Контроллер для работы с ресторанами
 */
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {


    /**
     * Получение информации о ресторане по его номеру
     */
    @GetMapping("/{id}")
    public void getRestaurantById(@PathVariable long id){

    }

    /**
     * Изменение информации о ресторане
     */
    @PutMapping("/{id}")
    public void updateRestaurant(@PathVariable long id
            , @RequestBody RestaurantUpdateInfoRequest restaurantInfo){

    }

    /**
     * Изменение статуса ресторана
     */
    @PutMapping("/{id}/status")
    public void updateRestaurantStatus(
            @RequestBody RestaurantStatusRequest restaurantStatus){

    }

    /**
     * Удаление ресторана из БД
     */
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable long id){

    }

    /**
     * Получение списка блюд конкретного ресторана
     */
    @GetMapping("/{idRest}/menuitems")
    public void getMenuItemAll(@PathVariable long idRest){

    }

}
