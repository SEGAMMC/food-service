package ru.liga.deliveryservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Order;
import ru.liga.common.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

        List<Order> getOrdersByStatus(OrderStatus status);

}
