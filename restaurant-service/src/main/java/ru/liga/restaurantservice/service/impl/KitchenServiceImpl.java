package ru.liga.restaurantservice.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.enums.OrderStatus;
import ru.liga.restaurantservice.dto.request.OrderStatusRequest;
import ru.liga.restaurantservice.dto.response.OrderItemForRestaurantResponse;
import ru.liga.restaurantservice.dto.response.OrderResponse;
import ru.liga.restaurantservice.feign_core.FeignToOrderService;
import ru.liga.restaurantservice.service.KitchenService;

/**
 * Сервис для работы кухни с заказами
 */
@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final FeignToOrderService feignToOrderService;

    /**
     * Обновление статуса заказа
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @Override
    public void updateOrderStatusByBody(UUID uuid, OrderStatusRequest orderStatus) {
        updateOrderStatus(uuid, orderStatus.getOrderStatus());
    }

    /**
     * Изменение статуса заказа на KITCHEN_ACCEPTED
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusAccept(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_ACCEPTED);
    }

    /**
     * Изменение статуса заказа на KITCHEN_PREPARING
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusPrepair(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_PREPARING);
    }

    /**
     * Изменение статуса заказа на DELIVERY_PENDING
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusPending(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.DELIVERY_PENDING);
    }

    /**
     * Изменение статуса заказа на KITCHEN_DENIED
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusDeny(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_DENIED);
    }

    /**
     * Изменение статуса заказа на KITCHEN_REFUNDED
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusRefund(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.KITCHEN_REFUNDED);
    }

    /**
     * Изменение статуса заказа на DELIVERY_PICKING
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public void updateOrderStatusPick(UUID uuid) {
        //TODO перед эти проверить назначен ли курьер в заказе
        //и что статус соответсвтует OrderStatus.DELIVERY_Pending
        updateOrderStatus(uuid, OrderStatus.DELIVERY_PICKING);
    }

    /**
     * Основной метод через который осуществляется изменение статуса
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    private void updateOrderStatus(UUID uuid, OrderStatus orderStatus) {
        feignToOrderService.updateOrderStatus(uuid, new OrderStatusRequest(orderStatus));
    }

    //Feign-methods

    /**
     * Получение списка позиций заказа для ресторана через OrderService
     *
     * @param uuid идентификационный номер заказа
     * @return возращает список позиций заказа и информация о них
     */
    @Override
    public List<OrderItemForRestaurantResponse> getOrderItemsByUuid(UUID uuid) {
        return feignToOrderService.getOrderItemsByUuid(uuid);
    }

    /**
     * Получение списка заказов для конкретного ресторана и статуса заказа
     *
     * @param restaurantId идентификационный номер ресторана
     * @param status       статус, по которому происходит фильтрация
     * @return список заказов имеющие нужный статус
     */
    @Override
    public List<OrderResponse> getOrderByRestaurantAndStatus(long restaurantId,
                                                             String status) {
        return feignToOrderService.getOrderByRestaurantAndStatus(restaurantId, status);
    }


}
