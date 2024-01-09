package ru.liga.notificationservice.service.rabbit;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.notificationservice.service.RabbitMQProducerService;

/**
 * Сервис предназначен для отправки сообщений с заказами в ресторан
 * и для отправки push-уведомлений клиентам
 */
@Service
@RequiredArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final String orderExchange = "OrdersExchange";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendOrderToOrderExchange(String routingKey, UUID orderId) {
        rabbitTemplate.convertAndSend(orderExchange, routingKey, orderId);
    }

}
