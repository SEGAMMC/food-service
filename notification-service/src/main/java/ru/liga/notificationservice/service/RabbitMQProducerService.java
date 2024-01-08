package ru.liga.notificationservice.service;

import java.util.UUID;

public interface RabbitMQProducerService {

    void sendOrderToOrderExchange(String routingKey, UUID orderId);

}
