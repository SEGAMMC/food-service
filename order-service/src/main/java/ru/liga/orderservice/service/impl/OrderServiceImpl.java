package ru.liga.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.*;
import ru.liga.common.enums.OrderStatus;
import ru.liga.orderservice.dto.request.*;
import ru.liga.orderservice.dto.response.OrderItemResponse;
import ru.liga.orderservice.dto.response.OrderPaymentResponse;
import ru.liga.orderservice.dto.response.OrderResponse;
import ru.liga.orderservice.dto.response.RestaurantResponse;
import ru.liga.orderservice.handler.NoSuchElementException;
import ru.liga.orderservice.repository.*;
import ru.liga.orderservice.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с заказами (Orders)
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //ToDO добавить функцию повторить заказ
    //
    //

    //TODO сделать с загрузкой из файла
    private String PREFIX_SECRET_PAYMENT_URL = "http://liga.ru/payment/order/";

    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    /**
     * Создание нового заказа
     *
     * @param customerId      - идентификационный номер клиента
     * @param newOrderRequest - параметры заказа
     * @return возвращает ответ с номером заказа, секретным кодом и временем доставки
     */
    @Override
    public OrderPaymentResponse createNewOrder(long customerId, NewOrderRequest newOrderRequest) {
        //TODO
        //		{ValidateNewOrderRequest}

        Restaurant restaurant = restaurantRepository
                .findById(newOrderRequest.getRestaurantId())
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение1"));
        LocalDateTime timeCreated = LocalDateTime.now();

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение1"));

        Order order = new Order();
        order = order.builder()
                .restaurantId(restaurant)
                .customerId(customer)
                .status(OrderStatus.CUSTOMER_CREATED)
                .timestamp(timeCreated)
                .build();

        order = orderRepository.save(order);
        mapOrderItemRequestToOrderItem(order, newOrderRequest.getOrderItems());

        OrderPaymentResponse orderPayment = new OrderPaymentResponse();
        orderPayment = orderPayment.builder()
                .id(order.getUuid())
                .secretPaymentUrl(PREFIX_SECRET_PAYMENT_URL + order.getUuid())
                .estimatedTimeOfArrival(timeCreated.plusMinutes(90))
                .build();
        return orderPayment;
    }

    /**
     * Получение списка всех заказов клиента
     *
     * @param customerId - идентификационный номер клиента
     * @return список заказов клиента
     */
    @Override
    public List<OrderResponse> getOrdersByCustomerId(long customerId) {
        //TODO Validate Customer Id

        List<Order> orders = orderRepository.getOrdersByCustomerId(customerId);
        List<OrderResponse> orderResponse = new ArrayList();

        for (Order order : orders) {
            orderResponse.add(mapOrderToOrderResponse(order));
        }
        return orderResponse;
    }

    /**
     * Получение информации о заказе по его номеру UUID
     *
     * @param uuid - идентификационный номер заказа
     * @return информация о заказе
     */
    @Override
    public OrderResponse getOrderByUuid(UUID uuid) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        return mapOrderToOrderResponse(order);
    }

    /**
     * Отмена заказа по номеру UUID
     *
     * @param uuid - идентификационный номер заказа
     */
    public void cancellOrder(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.CUSTOMER_CREATED);
        //TODO
        // запустить цепочку отмен оплат и возврата / оповещений всех

    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid        - идентификационный номер заказа
     * @param orderStatus - новый статус заказа (получает обертку OrderStatusRequest)
     */
    @Override
    public void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus) {
        updateOrderStatus(uuid, orderStatus.getOrderStatus());
    }

    /**
     * Добавление новой позиции в заказ
     *
     * @param uuid         - идентификационный номер заказа
     * @param newOrderItem - информация о новой позиции
     */
    public void addNewOrderItem(UUID uuid, AddOrderItemRequest newOrderItem) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        RestaurantMenuItem menuItem = menuItemRepository
                .findById(newOrderItem.getRestaurantMenuItem())
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        BigDecimal quantityDec = BigDecimal.valueOf(newOrderItem.getQuantity());
        BigDecimal priceOrderItem = menuItem.getPrice().multiply(quantityDec);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order);
        orderItem.setRestaurantMenuItem(menuItem);
        orderItem.setPrice(priceOrderItem);
        orderItem.setQuantity(newOrderItem.getQuantity());
        //TODO ввести проверку что заказ еще не начали изготовливать
        //TODO  ввессти проверку что в заказе такого Menu Item нет,
        // если есть то добавить к имеющемуся количеству
        orderItemRepository.save(orderItem);
    }

    /**
     * Удаление позиции из заказа
     *
     * @param uuid        - идентификационный номер заказа
     * @param orderItemId - номер позиции в заказе
     */
    public void deleteOrderItem(UUID uuid, long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        UUID orderUuidCheck = orderItem.getOrderId().getUuid();
        if (!orderUuidCheck.equals(uuid)) throw new NoSuchElementException("Данная позиция не найдена в заказе");
        //TODO ввести проверку что заказ еще не начали изготовливать
        orderItemRepository.delete(orderItem);
    }

    /**
     * Изменение количества блюд в позиции заказа
     *
     * @param uuid            - идентификационный номер заказа
     * @param orderItemId     - номер позиции в заказе
     * @param updateOrderItem - изменение количества данной позиции в заказ
     */
    public void updateOrderItem(UUID uuid, long orderItemId, UpdateOrderItemRequest updateOrderItem) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        RestaurantMenuItem menuItem = menuItemRepository.findById(orderItem.getRestaurantMenuItem().getId())
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        BigDecimal priceMenuItem = menuItem.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(updateOrderItem.getQuantity());
        BigDecimal priceOrderItem = priceMenuItem.multiply(quantity);

        UUID orderUuidCheck = orderItem.getOrderId().getUuid();
        if (!orderUuidCheck.equals(uuid)) throw new NoSuchElementException("Данная позиция не найдена в заказе");
        //TODO ввести проверку что заказ еще не начали изготовливать
        orderItem.setQuantity(updateOrderItem.getQuantity());
        orderItem.setPrice(priceOrderItem);
        orderItemRepository.save(orderItem);
    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid        - идентификационный номер заказа
     * @param orderStatus - новый статус заказа (получает enum OrderStatus)
     */
    private void updateOrderStatus(UUID uuid, OrderStatus orderStatus) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        order.setStatus(orderStatus);
        orderRepository.save(order);
    }

    /**
     * Маппинг ответа Order->OrderResponse
     *
     * @param order - информация о заказе (entity)
     * @return информация о заказе (response)
     */
    private OrderResponse mapOrderToOrderResponse(Order order) {
        List<OrderItemResponse> itemsResponse = new ArrayList();

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order);
        for (OrderItem orderItem : orderItems) {
            OrderItemResponse oir = new OrderItemResponse();
            oir = oir.builder()
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .build();
            itemsResponse.add(oir);
        }
        OrderResponse orderResponse = new OrderResponse();
        RestaurantResponse restaurantResponse = new RestaurantResponse(order.getRestaurantId().getName());
        orderResponse = orderResponse.builder()
                .id(order.getUuid())
                .items(itemsResponse)
                .restaurant(restaurantResponse)
                .timestamp(order.getTimestamp())
                .build();
        return orderResponse;
    }

    /**
     * Маппинг запроса OrderItemRequest->OrderItem
     *
     * @param order - информация о заказе (entity)
     * @param listOrderItemRequest - список позиций заказа из запроса (request)
     * @return список позиций в добавляемые в заказ  (entity)
     */
    private List<OrderItem> mapOrderItemRequestToOrderItem(Order order
            , List<OrderItemRequest> listOrderItemRequest) {
        List<OrderItem> listOrderItems = new ArrayList();

        for (OrderItemRequest oir : listOrderItemRequest) {
            RestaurantMenuItem rmi = menuItemRepository
                    .findById(oir.getMenuItemId())
                    .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));

            BigDecimal quantityDec = BigDecimal.valueOf(oir.getQuantity());
            BigDecimal priceOrderItem = rmi.getPrice().multiply(quantityDec);

            OrderItem orderItem = new OrderItem();
            orderItem = orderItem.builder()
                    .orderId(order)
                    .restaurantMenuItem(rmi)
                    .price(priceOrderItem)
                    .quantity(oir.getQuantity())
                    .build();

            listOrderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }
        return listOrderItems;
    }
}