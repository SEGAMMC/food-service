package ru.liga.orderservice.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.service.RabbitMQProducerService;

/**
 * Сервис предназначен для отправки сообщений с заказами в ресторан
 * и для отправки push-уведомлений клиентам
 */
@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final String notificationsExchange = "NotificationsExchange";
    private final String notificationsExchangeRoutingKey = "push.new";
    private final String deliveriesExchange = "DeliveriesExchange";
    private final String deliveriesExchangeRoutingKey = "order.delivery.new";
    private final String restaurantsExchange = "RestaurantsExchange";
    private final String restaurantsExchangeRoutingKey = "order.restaurant.new";

    private final RabbitTemplate rabbitTemplate;

    /**
     * Передача заказа через RabbitMQ в очередь в restaurant-service
     *
     * @param message информацию о заказе в строковом предствавлении
     */
    @Override
    public void sendOrderToRestaurantsExchange(String message) {
        rabbitTemplate.convertAndSend(restaurantsExchange,
                restaurantsExchangeRoutingKey, message);
    }

    /**
     * Передача заказа через RabbitMQ в очередь в delivery-service
     *
     * @param message информацию о заказе в строковом предствавлении
     */
    @Override
    public void sendOrderToDeliveriesExchange(String message) {
        rabbitTemplate.convertAndSend(deliveriesExchange,
                deliveriesExchangeRoutingKey, message);
    }

    /**
     * Передача push-уведомления через RabbitMQ в очередь в notification-service
     *
     * @param message информацию о заказе в строковом предствавлении
     */
    @Override
    public void sendPushToNotificationsExchange(String message) {
        rabbitTemplate.convertAndSend(notificationsExchange,
                notificationsExchangeRoutingKey, message);
    }

}
