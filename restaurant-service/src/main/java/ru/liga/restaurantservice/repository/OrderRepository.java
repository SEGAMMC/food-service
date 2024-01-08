package ru.liga.restaurantservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
