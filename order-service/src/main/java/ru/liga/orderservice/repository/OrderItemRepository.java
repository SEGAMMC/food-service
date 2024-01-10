package ru.liga.orderservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemByOrderId(Order order);
}
