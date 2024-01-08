package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     * @param customerId              - идентификационный номер клиента
     * @param newOrderRequest - параметры заказа
     * @return возвращает ответ с номером заказа, секретным кодом и временем доставки
     */
    @PostMapping("/customers/{id}/orders")
    public ResponseEntity<OrderPaymentResponse> createNewOrder(@PathVariable long customerId
            , @RequestBody NewOrderRequest newOrderRequest) {
        return ResponseEntity.ok(orderService.createNewOrder(customerId, newOrderRequest));
    }

    /**
     * Получение списка всех заказов клиента
     *
     * @param customerId - идентификационный номер клиента
     * @return список заказов клиента
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * Получение информации о заказе по его номеру UUID
     *
     * @param uuid - идентификационный номер заказа
     * @return информация о заказе
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponse> getOrderByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(orderService.getOrderByUuid(uuid));
    }

    /**
     * Отмена заказа по номеру UUID
     *
     * @param uuid - идентификационный номер заказа
     */
    @DeleteMapping("/{uuid}")
    public void cancellOrder(@PathVariable UUID uuid) {
        orderService.cancellOrder(uuid);
    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid - идентификационный номер заказа
     * @param orderStatus - новый статус заказа
     */
    @PutMapping("/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus) {
        orderService.updateOrderStatus(uuid, orderStatus);
    }
}