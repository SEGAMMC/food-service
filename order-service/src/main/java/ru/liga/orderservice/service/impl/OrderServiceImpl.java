package ru.liga.orderservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.*;
import ru.liga.common.enums.ActionType;
import ru.liga.common.enums.MessageType;
import ru.liga.common.enums.OrderStatus;
import ru.liga.orderservice.dto.request.*;
import ru.liga.orderservice.dto.response.*;
import ru.liga.orderservice.feign_core.FeignToRestaurant;
import ru.liga.orderservice.handler.NoSuchElementException;
import ru.liga.orderservice.repository.CustomerRepository;
import ru.liga.orderservice.repository.OrderItemRepository;
import ru.liga.orderservice.repository.OrderRepository;
import ru.liga.orderservice.service.OrderService;
import ru.liga.orderservice.service.RabbitMQProducerService;

/**
 * Сервис для работы с заказами (Orders)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //ToDO добавить функцию повторить заказ
    //
    //

    //TODO сделать с загрузкой из файла

    //TODO рассмотреть ситуацию когда заказ оплатили и он был передан
    // в ресторан, а от него решили отказаться

    //TODO придумать как будет проходить оплата
    private static final String PREFIX_SECRET_PAYMENT_URL = "http://liga.ru/"
            + "payment/order/";
    private static final int DEFAULT_TIME_DELIVERY = 90;

    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    private final FeignToRestaurant feignToRestaurant;

    private final RabbitMQProducerService rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    /**
     * Создание нового заказа
     *
     * @param customerId      идентификационный номер клиента
     * @param newOrderRequest параметры заказа
     * @return возвращает ответ с номером заказа, секретным кодом и временем доставки
     */
    @Override
    public OrderPaymentResponse createNewOrder(long customerId,
                                               NewOrderRequest newOrderRequest) {
        //TODO
        //ValidateNewOrderRequest
        //TODO  ввести проверку того что клиент существует и может делать заказы
        //TODO  ввести проверку того что позиции заказа соответствуют пунктам меню
        //TODO  можно добавить возможность при создании заказа указывать на какой
        // адресс будет отправляться заказ
        //TODO можно добавить возможность изменения адресса доставки
        // до начала его доставки

        //TODO  ввести проверку того ресторан открыт и сможет приготовить до его закрытия


        Restaurant restaurant = feignToRestaurant
                .getRestaurantByIdForService(newOrderRequest.getRestaurantId());

        LocalDateTime timeCreated = LocalDateTime.now();

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение1"));

        Order order = Order.builder()
                .restaurantId(restaurant)
                .customerId(customer)
                .status(OrderStatus.CUSTOMER_CREATED)
                .timestamp(timeCreated)
                .build();
        order = orderRepository.save(order);


        //TODO ввести перенос OrderItem из запроса!!!!!


        //Отправка push клиенту
        ModelMessageOrder modelMessageOrder = mapOrderToModelMessageOrder(order,
                MessageType.PUSH, ActionType.ORDER_CUSTOMER_CREATED);
        String messageToString = mapModelMessageToString(modelMessageOrder);
        rabbitMQProducerService.sendPushToNotificationsExchange(messageToString);

        return OrderPaymentResponse.builder()
                .id(order.getUuid())
                .secretPaymentUrl(PREFIX_SECRET_PAYMENT_URL + order.getUuid())
                .estimatedTimeOfArrival(timeCreated.plusMinutes(DEFAULT_TIME_DELIVERY))
                .build();
    }

    /**
     * Получение списка всех заказов клиента
     *
     * @param customerId идентификационный номер клиента
     * @return список заказов клиента
     */
    @Override
    public List<OrderResponse> getOrdersByCustomerId(long customerId) {
        //TODO Validate Customer Id

        List<Order> orders = orderRepository.getOrdersByCustomerId(customerId);
        List<OrderResponse> orderResponse = new ArrayList<>();

        for (Order order : orders) {
            orderResponse.add(mapOrderToOrderResponse(order));
        }
        return orderResponse;
    }

    /**
     * Получение информации о заказе по его номеру UUID
     *
     * @param uuid идентификационный номер заказа
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
     * @param uuid идентификационный номер заказа
     */
    public void cancellOrder(UUID uuid) {
        updateOrderStatus(uuid, OrderStatus.CUSTOMER_CREATED);
        //TODO
        // запустить цепочку отмен оплат и возврата / оповещений всех

    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа (получает обертку OrderStatusRequest)
     */
    @Override
    public boolean updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus) {
        return updateOrderStatus(uuid, orderStatus.getOrderStatus());
    }

    /**
     * Клиент подтверждает что заказ доставили
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public boolean updateOrderStatusComplete(UUID uuid) {
        return updateOrderStatus(uuid,
                new OrderStatusRequest(OrderStatus.DELIVERY_COMPLETE));
    }


    /**
     * Добавление новой позиции в заказ
     *
     * @param uuid         идентификационный номер заказа
     * @param newOrderItem информация о новой позиции
     */
    public void addNewOrderItem(UUID uuid, AddOrderItemRequest newOrderItem) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        RestaurantMenuItem menuItem = feignToRestaurant
                .getMenuItemByIdForService(newOrderItem.getRestaurantMenuItem());

        BigDecimal quantityDec = BigDecimal.valueOf(newOrderItem.getQuantity());
        BigDecimal priceOrderItem = menuItem.getPrice().multiply(quantityDec);

        OrderItem orderItem = OrderItem.builder()
                .orderId(order)
                .restaurantMenuItem(menuItem)
                .price(priceOrderItem)
                .quantity(newOrderItem.getQuantity())
                .build();
        //TODO ввести проверку что заказ еще не начали изготовливать
        //TODO  ввессти проверку что в заказе такого Menu Item нет,
        // если есть то добавить к имеющемуся количеству
        orderItemRepository.save(orderItem);
    }

    /**
     * Удаление позиции из заказа
     *
     * @param uuid        идентификационный номер заказа
     * @param orderItemId номер позиции в заказе
     */
    public void deleteOrderItem(UUID uuid, long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        UUID orderUuidCheck = orderItem.getOrderId().getUuid();
        if (!orderUuidCheck.equals(uuid)) {
            throw new NoSuchElementException("Данная позиция не найдена в заказе");
        }
        //TODO ввести проверку что заказ еще не начали изготовливать
        orderItemRepository.delete(orderItem);
    }

    /**
     * Изменение количества блюд в позиции заказа
     *
     * @param uuid            идентификационный номер заказа
     * @param orderItemId     номер позиции в заказе
     * @param updateOrderItem изменение количества данной позиции в заказ
     */
    public void updateOrderItem(UUID uuid, long orderItemId,
                                UpdateOrderItemRequest updateOrderItem) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        RestaurantMenuItem menuItem = feignToRestaurant
                .getMenuItemByIdForService(orderItem.getRestaurantMenuItem().getId());

        BigDecimal priceMenuItem = menuItem.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(updateOrderItem.getQuantity());
        BigDecimal priceOrderItem = priceMenuItem.multiply(quantity);

        UUID orderUuidCheck = orderItem.getOrderId().getUuid();
        if (!orderUuidCheck.equals(uuid)) {
            throw new NoSuchElementException("Данная позиция не найдена в заказе");
        }
        //TODO ввести проверку что заказ еще не начали изготовливать
        orderItem.setQuantity(updateOrderItem.getQuantity());
        orderItem.setPrice(priceOrderItem);
        orderItemRepository.save(orderItem);
    }

    /**
     * Получение списка позиций заказа для ресторана
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public List<OrderItemForRestaurantResponse> getOrderItemsByUuid(UUID uuid) {
        //TODO проверить

        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        List<OrderItem> orderItemList = orderItemRepository.findOrderItemByOrderId(order);

        List<OrderItemForRestaurantResponse> oirListResponses = new ArrayList<>();

        for (OrderItem oi : orderItemList) {
            MenuItemForListResponse menuItem = MenuItemForListResponse.builder()
                    .id(oi.getRestaurantMenuItem().getId())
                    .name(oi.getRestaurantMenuItem().getName())
                    .description(oi.getRestaurantMenuItem().getDescription())
                    .price(oi.getPrice())
                    .imageUrl(oi.getRestaurantMenuItem().getImageUrl())
                    .build();

            OrderItemForRestaurantResponse oiResponse = OrderItemForRestaurantResponse
                    .builder()
                    .orderItemId(oi.getId())
                    .menuItem(menuItem)
                    .quantity(oi.getQuantity())
                    .build();
            oirListResponses.add(oiResponse);
        }
        return oirListResponses;
    }

    /**
     * Получение информации о заказе для курьера через OrderService
     *
     * @param uuid идентификационный номер заказа
     */
    @Override
    public OrderInfo getOrderInfoByUuidForCouriers(UUID uuid) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        return OrderInfo.builder()
                .orderId(order.getUuid())
                .customerAddress(order.getCustomerId().getAddress())
                .phoneCustomer(order.getCustomerId().getPhone())
                .restaurantName(order.getRestaurantId().getName())
                .restaurantAddress(order.getRestaurantId().getAddress())
                .deliveryTime(order.getTimestamp().plusMinutes(DEFAULT_TIME_DELIVERY))
                .build();
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
        //TODO ввести проверку и определение статуса что такой есть
        Restaurant restaurant = feignToRestaurant
                .getRestaurantByIdForService(restaurantId);
        OrderStatus orderStatus = mapOrderStatusForRestaurant(status);

        List<Order> ordersByRestaurantIdAndStatus = orderRepository
                .getOrdersByRestaurantIdAndStatus(restaurant, orderStatus);
        List<OrderResponse> ordersList = new ArrayList<>();
        for (Order order : ordersByRestaurantIdAndStatus) {
            ordersList.add(mapOrderToOrderResponse(order));
        }
        return ordersList;
    }

    /**
     * Добавление курьера в заказ
     *
     * @param uuid    идентификационный номер заказа
     * @param courier сущность курьера, которому назначают заказ
     */
    @Override
    public void addCourierInOrder(UUID uuid, Courier courier) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        order.setCourierId(courier);
        order.setStatus(OrderStatus.DELIVERY_PICKING);
        order = orderRepository.save(order);

        ModelMessageOrder messageOrder = mapOrderToModelMessageOrder(order,
                MessageType.PUSH, ActionType.ADD_COURIER);
        String modelMessageToString = mapModelMessageToString(messageOrder);
        rabbitMQProducerService.sendPushToNotificationsExchange(modelMessageToString);
    }

    /**
     * Изменение статуса заказа номеру UUID
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа (получает enum OrderStatus)
     */
    private boolean updateOrderStatus(UUID uuid, OrderStatus orderStatus) {


        //TODO прежде чем менять проверить что он уже не такой же как был
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        order.setStatus(orderStatus);
        orderRepository.save(order);

        Order orderCheck = orderRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));


        //TODO заменить на списки со статусами
        if (orderCheck.getStatus().equals(orderStatus)) {
            ModelMessageOrder messageOrder = null;
            String modelMessageToString = null;
            if (orderStatus.equals(OrderStatus.CUSTOMER_PAID)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_CUSTOMER_PAID);
            }
            if (orderStatus.equals(OrderStatus.CUSTOMER_CANCELLED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_CUSTOMER_CANCELLED);
            }
            if (orderStatus.equals(OrderStatus.KITCHEN_ACCEPTED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_KITCHEN_ACCEPTED);
            }
            if (orderStatus.equals(OrderStatus.KITCHEN_PREPARING)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_KITCHEN_PREPARING);
            }
            if (orderStatus.equals(OrderStatus.KITCHEN_DENIED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_KITCHEN_DENIED);
            }
            if (orderStatus.equals(OrderStatus.KITCHEN_REFUNDED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_KITCHEN_REFUNDED);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_PICKING)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_PICKING);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_DELIVERING)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_DELIVERING);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_COMPLETE)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_COMPLETE);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_DENIED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_DENIED);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_REFUNDED)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_REFUNDED);
            }
            if (orderStatus.equals(OrderStatus.DELIVERY_PENDING)) {
                messageOrder = mapOrderToModelMessageOrder(order,
                        MessageType.PUSH, ActionType.ORDER_DELIVERY_PENDING);
                modelMessageToString = mapModelMessageToString(messageOrder);
                rabbitMQProducerService.sendOrderToDeliveriesExchange(
                        modelMessageToString);
            }
            modelMessageToString = mapModelMessageToString(messageOrder);
            rabbitMQProducerService.sendPushToNotificationsExchange(modelMessageToString);
            return true;
        }
        return false;
    }

    /**
     * Маппинг ответа Order->OrderResponse
     *
     * @param order информация о заказе (entity)
     * @return информация о заказе (response)
     */
    private OrderResponse mapOrderToOrderResponse(Order order) {
        List<OrderItemResponse> itemsResponse = new ArrayList<>();


        //TODO Переделать чтобы окончательно формировал ответ restaurant service
        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order);
        for (OrderItem orderItem : orderItems) {
            OrderItemResponse oir = OrderItemResponse.builder()
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .build();
            itemsResponse.add(oir);
        }
        RestaurantResponse restaurantResponse = new RestaurantResponse(order
                .getRestaurantId().getName());
        return OrderResponse.builder()
                .id(order.getUuid())
                .items(itemsResponse)
                .restaurant(restaurantResponse)
                .timestamp(order.getTimestamp())
                .build();
    }

    /**
     * Маппинг запроса OrderItemRequest->OrderItem
     *
     * @param order                информация о заказе (entity)
     * @param listOrderItemRequest список позиций заказа из запроса (request)
     * @return список позиций в добавляемые в заказ  (entity)
     */
    private List<OrderItem> mapOrderItemRequestToOrderItem(
            Order order, List<OrderItemRequest> listOrderItemRequest) {
        List<OrderItem> listOrderItems = new ArrayList<>();

        List<Long> menuItems = new ArrayList<>();
        for (OrderItemRequest oir : listOrderItemRequest) {
            menuItems.add(oir.getMenuItemId());
        }

        List<RestaurantMenuItem> listMenuItems = feignToRestaurant
                .getListMenuItemForService(menuItems);
        Iterator<RestaurantMenuItem> iterItem = listMenuItems.iterator();

        for (OrderItemRequest oir : listOrderItemRequest) {
            RestaurantMenuItem rmi = iterItem.next();
            BigDecimal quantityDec = BigDecimal.valueOf(oir.getQuantity());
            BigDecimal priceOrderItem = rmi.getPrice().multiply(quantityDec);

            OrderItem orderItem = OrderItem.builder()
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


    /**
     * Переводит status из запроса от ресторана в соответствующий OrderStatus
     *
     * @param status статус из запроса от ресторана в формате String
     * @return возвращает соответствующий статус в формате OrderStatus
     */
    private OrderStatus mapOrderStatusForRestaurant(String status) {
        //TODO перенести это в китчен сервис

        OrderStatus orderStatus = null;
        if (status.equals("new")) {
            orderStatus = OrderStatus.CUSTOMER_PAID;
        }
        if (status.equals("cancel")) {
            orderStatus = OrderStatus.CUSTOMER_CANCELLED;
        }
        if (status.equals("accept")) {
            orderStatus = OrderStatus.KITCHEN_ACCEPTED;
        }
        if (status.equals("deny")) {
            orderStatus = OrderStatus.KITCHEN_DENIED;
        }
        if (status.equals("prepare")) {
            orderStatus = OrderStatus.KITCHEN_PREPARING;
        }
        if (status.equals("refund")) {
            orderStatus = OrderStatus.KITCHEN_REFUNDED;
        }
        if (status.equals("complete")) {
            orderStatus = OrderStatus.DELIVERY_PENDING;
        }
        return orderStatus;
    }

    /**
     * Маппинг для сообщений push - уведомлений Order->ModelMessageOrder
     *
     * @param order       информация о заказе (entity)
     * @param messageType тип сообщения
     * @param actionType  тип события о котором надо оповестить
     * @return возвращается ответ для оправки push-сообщения  (entity)
     */
    private ModelMessageOrder mapOrderToModelMessageOrder(
            Order order,
            MessageType messageType, ActionType actionType) {
        return ModelMessageOrder.builder()
                .uuid(order.getUuid())
                .customerId(order.getCustomerId().getId())
                .restaurantId(order.getRestaurantId().getId())
                .courierId(order
                        .getCourierId() == null ? 0 : order.getCourierId().getId())
                .orderStatus(order.getStatus())
                .type(messageType)
                .action(actionType)
                .build();
    }

    /**
     * Маппинг сообщения типа ModelMessageOrder в String
     *
     * @param modelMessageOrder объект сообщения для push-уведомления (entity)
     * @return возвращается объект сообщения в формате String  (entity)
     */
    private String mapModelMessageToString(ModelMessageOrder modelMessageOrder) {
        String messageModelToString = null;
        try {
            messageModelToString = objectMapper.writeValueAsString(modelMessageOrder);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return messageModelToString;
    }
}
