package ru.liga.orderservice.service;

public interface RabbitMQProducerService {

    void sendPushToNotificationsExchange(String message);

    void sendOrderToRestaurantsExchange(String message);

    void sendOrderToDeliveriesExchange(String message);
}
