package ru.liga.restaurantservice.service;

public interface RabbitMQProducerService {

    void sendPushToNotificationsExchange(String message);

}
