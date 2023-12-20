package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {



}
