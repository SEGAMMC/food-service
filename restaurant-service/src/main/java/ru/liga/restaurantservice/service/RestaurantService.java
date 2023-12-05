package ru.liga.restaurantservice.service;

import java.util.UUID;

public interface RestaurantService {
    void getRestaurantById(UUID uuid);

    void updateRestaurant();
}
