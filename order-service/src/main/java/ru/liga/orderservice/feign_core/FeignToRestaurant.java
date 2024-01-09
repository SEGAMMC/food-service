package ru.liga.orderservice.feign_core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.entity.RestaurantMenuItem;

import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082/api/v1/restaurants")
public interface FeignToRestaurant {

    /**
     * Получение информации о ресторане от restaurant-service
     *
     * @param restaurantId идентификационный номер ресторана
     * @return возвращает информацию о ресторане
     */
    @GetMapping("/{restaurantId}/service")
    Restaurant getRestaurantByIdForService(@PathVariable long restaurantId);

    /**
     * Получение информации о блюде из меню ресторана от restaurant-service
     *
     * @param menuItemId идентификационный номер блюда в меню
     * @return возвращает информацию о блюде из ресторана
     */
    @GetMapping("/menuitems/{menuItemId}/service")
    RestaurantMenuItem getMenuItemByIdForService(@PathVariable long menuItemId);

    /**
     * Получение списка информации о блюде из меню ресторана от restaurant-service
     *
     * @param listMenuItemId список идентификационных номеров блюд в меню
     * @return возвращает информацию о блюде из ресторана
     */
    @PostMapping("/menuitems/list/service")
    List<RestaurantMenuItem> getListMenuItemForService(
            @RequestBody List<Long> listMenuItemId);
}

