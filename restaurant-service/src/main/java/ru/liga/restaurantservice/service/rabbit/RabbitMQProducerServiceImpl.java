package ru.liga.restaurantservice.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.restaurantservice.service.RabbitMQProducerService;

/**
 * Сервис предназначен для отправки сообщений с заказами в ресторан
 * и для отправки push-уведомлений клиентам
 */
@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final String notificationsExchange = "NotificationsExchange";
    private final String notificationsExchangeRoutingKey = "push.new";

    private final RabbitTemplate rabbitTemplate;


    /**
     * Передача push-уведомления через RabbitMQ в очередь в notification-service
     *
     * @param message информацию о заказе в строковом предствавлении
     */
    @Override
    public void sendPushToNotificationsExchange(String message) {
        rabbitTemplate.convertAndSend(notificationsExchange, notificationsExchangeRoutingKey, message);
    }

}
