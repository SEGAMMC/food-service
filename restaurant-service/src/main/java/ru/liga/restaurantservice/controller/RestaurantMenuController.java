package ru.liga.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.MenuItemRequest;
import ru.liga.restaurantservice.dto.request.UpdatePriceMenuItemRequest;

/**
 * Контроллер для работы с меню ресторана
 */
@RestController
@RequestMapping("/api/v1/restaurants/menuitems")
public class RestaurantMenuController {

    /**
     * Получение информации о блюде по его номеру
     */
    @GetMapping("/{idItem}")
    public void getMenuItemById(@PathVariable long idItem){

    }

	/**
     * Добавление нового блюда в меню ресторана
     */
    @PostMapping
    public void createNewMenuItem( @RequestBody MenuItemRequest newMenuItem){

    }

	/**
     * Изменение информации о блюде в меню ресторана
     */
    @PutMapping("/{id}")
    public void updateMenuItem(@PathVariable long id
            , @RequestBody MenuItemRequest updateMenuItem){

    }

    /**
     * Изменение цены блюда в меню ресторана
     */
    @PutMapping("/{id}/price")
    public void updatePriceItem(@PathVariable long id
            , @RequestBody UpdatePriceMenuItemRequest updatePriceMenuItem){

    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable long id){

    }


}
