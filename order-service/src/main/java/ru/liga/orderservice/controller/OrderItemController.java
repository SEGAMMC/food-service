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
 * Контроллер для работы позициями в заказе
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderItemController {

    /**
     *  Добавление новой позиции в заказ
     */
//    @PostMapping("/{uuid}/items")
//    public void addNewItemInOrder(@PathVariable UUID uuid
//            , @RequestBody NewOrderRequest newOrder){
//
//    }

    /**
     *  Удаление позиции в заказ
     */
//    @DeleteMapping("/{uuid}/items")
//    public void deleteItemInOrder(@PathVariable UUID uuid
//            , @RequestBody NewOrderRequest newOrder){
//
//    }

}



