package ru.liga.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
