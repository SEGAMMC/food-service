package ru.liga.common.batisMapper.orderItem;

import ru.liga.common.entity.OrderItem;
import ru.liga.common.entity.RestaurantMenuItem;

public interface OrderItemMapper {
    OrderItem getOrderItemById(Long id);

    RestaurantMenuItem getRestaurantMenuItemById(Long id);

}
