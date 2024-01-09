package ru.liga.deliveryservice.feign_core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderInfo;
import ru.liga.deliveryservice.dto.request.OrderStatusRequest;
import ru.liga.deliveryservice.dto.request.OrdersByStatusAndCoordsRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service", url = "http://localhost:8081/api/v1/orders")
public interface FeignToOrderService {

    /**
     * Получение информации о заказе для курьера через OrderService
     *
     * @param uuid идентификационный номер заказа
     */
    @GetMapping("/{uuid}/couriers")
    OrderInfo getOrderInfoByUuid(@PathVariable UUID uuid);

    /**
     * Изменение статуса заказа номеру UUID через OrderService
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @PutMapping("/{uuid}/status")
    void updateOrderStatus(@PathVariable UUID uuid,
                           @RequestBody OrderStatusRequest orderStatus);

    /**
     * Получение списка доступных заказов для доставки с соответствующим
     * статусом заказа через Order service
     *
     * @param orderByStatusAndCoords координаты и статус заказа доступные для доставки
     */
    @PostMapping("/status")
    List<Order> getOrderListByStatus(
            OrdersByStatusAndCoordsRequest orderByStatusAndCoords);

    /**
     * Добавление курьера в заказ
     *
     * @param uuid    идентификационный номер заказа
     * @param courier сущность курьера, которому назначают заказ
     */
    @PutMapping("/{uuid}/couriers")
    void addCourierInOrder(@PathVariable UUID uuid, @RequestBody Courier courier);

}

