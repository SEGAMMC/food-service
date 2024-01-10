package ru.liga.deliveryservice.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.service.RabbitMQProducerService;

/**
 * Сервис предназначен для отправки сообщений с заказами в ресторан
 * и для отправки push-уведомлений клиентам
 */
@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final static String NOTIFICATIONS_EXCHANGE = "NotificationsExchange";
    private final static String NOTIFICATIONS_EXCHANGE_ROUTING_KEY = "push.new";

    private final RabbitTemplate rabbitTemplate;


    /**
     * Передача push-уведомления через RabbitMQ в очередь в notification-service
     *
     * @param message информацию о заказе в строковом предствавлении
     */
    @Override
    public void sendPushToNotificationsExchange(String message) {
        rabbitTemplate.convertAndSend(NOTIFICATIONS_EXCHANGE,
                NOTIFICATIONS_EXCHANGE_ROUTING_KEY, message);
    }

}
