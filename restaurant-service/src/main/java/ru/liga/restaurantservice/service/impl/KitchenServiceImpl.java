package ru.liga.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Order;
import ru.liga.common.enums.OrderStatus;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.repository.OrderRepository;
import ru.liga.restaurantservice.service.KitchenService;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Сервис для работы кухни с заказами
 */
@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final OrderRepository orderRepository;

    /**
     * Обновление статуса заказа
     *
     * @param uuid        - идентификационный номер заказа
     * @param orderStatus - новый статус заказа
     */
    @Override
    public void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus) {
        updateOrderStatus(uuid, orderStatus.getStatus());
    }

    /**
     * Изменение статуса заказа на KITCHEN_ACCEPTED
     *
     * @param uuid - идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusAccept(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_ACCEPTED);
    }

    /**
     * Изменение статуса заказа на KITCHEN_PREPARING
     *
     * @param uuid - идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusPrepair(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_PREPARING);
    }

    /**
     * Изменение статуса заказа на KITCHEN_DENIED
     *
     * @param uuid - идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusDeny(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_DENIED);
    }

    /**
     * Изменение статуса заказа на KITCHEN_REFUNDED
     *
     * @param uuid - идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusRefund(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_REFUNDED);
    }

    private void updateOrderStatus(UUID uuid, OrderStatus orderStatus) {
        Order order = orderRepository.findOrderByUuid(uuid);
        if (order == null) throw new NoSuchElementException("Напишите сообщение");
        order.setStatus(orderStatus);
        orderRepository.save(order);
    }
}
