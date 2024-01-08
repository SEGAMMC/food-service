package ru.liga.common.batisMapper.order;

import ru.liga.common.entity.Order;

public interface OrderMapper {
    Order getOrderById(Long id);

    void updateOrderStatus(Order order);
}
