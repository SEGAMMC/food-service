package ru.liga.common.batisMapper.restaurant;

import ru.liga.common.entity.Restaurant;

public interface RestaurantMapper {
    Restaurant getRestaurantById(Long id);

    String getNameRestaurantById(Long id);

}
