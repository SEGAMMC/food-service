package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
