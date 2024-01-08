package ru.liga.deliveryservice.service;

public interface RabbitMQProducerService {

    void sendPushToNotificationsExchange(String message);

}
