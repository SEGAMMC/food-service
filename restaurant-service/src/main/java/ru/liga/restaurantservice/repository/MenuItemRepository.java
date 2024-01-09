package ru.liga.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.entity.RestaurantMenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

    List<RestaurantMenuItem> getRestaurantMenuItemsByRestaurantId(Restaurant restaurant);

}
