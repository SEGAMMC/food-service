package ru.liga.restaurantservice.service;

import ru.liga.common.entity.Restaurant;
import ru.liga.restaurantservice.dto.request.RestaurantStatusRequest;
import ru.liga.restaurantservice.dto.request.RestaurantUpdateInfoRequest;
import ru.liga.restaurantservice.dto.response.MenuItemForListResponse;
import ru.liga.restaurantservice.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse getRestaurantById(long restaurantId);

    void updateRestaurantById(long restaurantId
            , RestaurantUpdateInfoRequest restaurantInfo);

    void updateRestaurantStatus(long restaurantId
            , RestaurantStatusRequest restaurantStatus);

    void updateRestaurantStatusActive(long restaurantId);

    void updateRestaurantStatusInactive(long restaurantId);

    void deleteRestaurant(long restaurantId);

    List<MenuItemForListResponse> getMenuItemsRestaurant(long restaurantId);

    Restaurant getRestaurantByIdForService(long restaurantId);

}