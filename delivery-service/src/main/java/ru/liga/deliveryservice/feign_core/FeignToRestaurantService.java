package ru.liga.deliveryservice.feign_core;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082/api/v1/restaurants")
public interface FeignToRestaurantService {

    /**
     * Оповещение ресторана, что курьер скоро прибудет
     * за заказом с номером UUID через RestaurantService
     *
     * @param uuid идентификационный номер заказа
     */
    @PostMapping("/orders/{uuid}/pushdelivery")
    void sendPushDeliveryToRestaurant(@PathVariable UUID uuid);

}
