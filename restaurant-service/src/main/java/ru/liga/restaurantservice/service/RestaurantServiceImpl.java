package ru.liga.restaurantservice.service;

import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.request.NewOrderRequest;
import ru.liga.orderservice.dto.request.OrderStatusRequest;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с ресторанами
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {


    /**
     * Получение информации о ресторане по UUID
     */
    @Override
    public void getRestaurantById(UUID uuid) {

    }

    /**
     * Изменение информации о ресторане
     */
    @Override
    public void updateRestaurant() {

    }
}
