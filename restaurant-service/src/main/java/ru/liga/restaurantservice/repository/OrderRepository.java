package ru.liga.restaurantservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.entity.RestaurantMenuItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderByUuid(UUID uuid);

}
