package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.RestaurantMenuItem;

public interface MenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {



}
