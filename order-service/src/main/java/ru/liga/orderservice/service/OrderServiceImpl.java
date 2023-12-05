package ru.liga.orderservice.service;

import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.request.NewOrderRequest;
import ru.liga.orderservice.dto.request.OrderStatusRequest;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с заказами
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Получение информации заказа по его номеру UUID
     */
    @Override
    public OrderResponse getOrderById(UUID uuid) {
        return null;
    }

    /**
     * Получение всех заказов клиента
     */
    @Override
    public List<OrderResponse> getAllOrders() {
        return null;
    }

    /**
     *  Создание нового заказа
     */
    @Override
    public OrderPaymentResponse createNewOrder(NewOrderRequest newOrder) {
        return null;
    }

    /**
     * Отмена заказа по номеру UUID
     */
    @Override
    public void cancellOrder(UUID uuid) {

    }

    /**
     * Изменение статуса заказа номеру UUID
     */
    @Override
    public void updateOrderStatus(OrderStatusRequest orderStatus) {

    }
}
