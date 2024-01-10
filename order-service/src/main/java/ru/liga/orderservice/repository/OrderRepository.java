package ru.liga.orderservice.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.enums.OrderStatus;

/**
 * Репозиторий для работы с заказами (Order)
 */

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Order getOrderByUuid(UUID uuid);

    List<Order> getOrdersByCustomerId(long id);

    List<Order> getOrdersByRestaurantIdAndStatus(Restaurant restaurant,
                                                 OrderStatus orderStatus);

}
