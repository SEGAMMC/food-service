package ru.liga.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Order;
import ru.liga.common.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

        List<Order> getOrdersByStatus(OrderStatus status);

}
