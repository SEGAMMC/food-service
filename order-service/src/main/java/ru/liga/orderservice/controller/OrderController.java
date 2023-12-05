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
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     *  Создание нового заказа
     */
    @PostMapping
    public ResponseEntity<OrderPaymentResponse> createNewOrder(@RequestBody NewOrderRequest newOrder){
        OrderPaymentResponse orderPayment = orderService.createNewOrder(newOrder);
        return ResponseEntity.ok(orderPayment);
    }

    /**
     * Получение всех заказов клиента
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Получение информации заказа по его номеру UUID
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID uuid){
        OrderResponse order = orderService.getOrderById(uuid);
        return ResponseEntity.ok(order);
    }

    /**
     * Отмена заказа по номеру UUID
     */
    @DeleteMapping("/{uuid}")
    public void cancellOrder(@PathVariable UUID uuid){
        orderService.cancellOrder(uuid);
    }

    /**
     * Изменение статуса заказа номеру UUID
     */
    @PutMapping("/{uuid}/status")
    public void updateOrderStatus(@PathVariable UUID uuid, @RequestBody OrderStatusRequest orderStatus){
        orderService.updateOrderStatus(orderStatus);
    }


}



