package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.OrderInfo;
import ru.liga.orderservice.dto.request.NewOrderRequest;
import ru.liga.orderservice.dto.request.OrderStatusRequest;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;
import ru.liga.orderservice.service.OrderService;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с заказами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    /**
     * Создание нового заказа
     *
     * @param customerId      идентификационный номер клиента
     * @param newOrderRequest параметры заказа
     * @return возвращает ответ с номером заказа, секретным кодом и временем доставки
     */
    @PostMapping("/customers/{customerId}/orders")
    public ResponseEntity<OrderPaymentResponse> createNewOrder(
            @PathVariable long customerId,
            @RequestBody NewOrderRequest newOrderRequest) {
        return ResponseEntity.ok(orderService.createNewOrder(customerId,
                newOrderRequest));
    }

    /**
     * Получение списка всех заказов клиента
     *
     * @param customerId идентификационный номер клиента
     * @return список заказов клиента
     */
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(
            @PathVariable long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * Получение информации о заказе по его номеру UUID
     *
     * @param uuid идентификационный номер заказа
     * @return информация о заказе
     */
    @GetMapping("/orders/{uuid}")
    public ResponseEntity<OrderResponse> getOrderByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(orderService.getOrderByUuid(uuid));
    }

    /**
     * Отмена заказа по номеру UUID
     *
     * @param uuid идентификационный номер заказа
     */
    @DeleteMapping("/orders/{uuid}")
    public void cancellOrder(@PathVariable UUID uuid) {
        orderService.cancellOrder(uuid);
    }

    //FEIGN methods

    /**
     * Получение информации о заказе для курьера через OrderService
     *
     * @param uuid идентификационный номер заказа
     */
    @GetMapping("/orders/{uuid}/couriers")
    public OrderInfo getOrderInfoByUuid(@PathVariable UUID uuid) {
        return orderService.getOrderInfoByUuidForCouriers(uuid);
    }

    /**
     * Получение списка заказов для конкретного ресторана и статуса заказа
     *
     * @param restaurantId идентификационный номер ресторана
     * @param status       статус, по которому происходит фильтрация
     * @return список заказов имеющие нужный статус
     */
    @GetMapping("/orders")
    public List<OrderResponse> getOrderByRestaurantAndStatus(
            @RequestParam long restaurantId,
            @RequestParam String status) {
        return orderService.getOrderByRestaurantAndStatus(restaurantId, status);
    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @PutMapping("/orders/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid,
                                  @RequestBody OrderStatusRequest orderStatus) {
        orderService.updateOrderStatus(uuid, orderStatus);
    }

    /**
     * Клиент подтверждает что заказ доставили
     *
     * @param uuid идентификационный номер заказа
     */
    @PutMapping("/orders/{uuid}/status/complete")
    public void updateOrderStatusComplete(@PathVariable UUID uuid) {
        orderService.updateOrderStatusComplete(uuid);
    }

    /**
     * Добавление курьера в заказ
     *
     * @param uuid    идентификационный номер заказа
     * @param courier сущность курьера, которому назначают заказ
     */
    @PutMapping("/orders/{uuid}/couriers")
    public void addCourierInOrder(@PathVariable UUID uuid, @RequestBody Courier courier) {
        //TODO заменить ентити на dto
        orderService.addCourierInOrder(uuid, courier);
    }
}
