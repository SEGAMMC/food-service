package ru.liga.restaurantservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.common.enums.ActionType;
import ru.liga.common.enums.MessageType;
import ru.liga.restaurantservice.service.ListenerService;
import ru.liga.restaurantservice.service.RabbitMQProducerService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final RabbitMQProducerService rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    /**
     * Метод отслеживающий очередь OrdersNewForRestaurant RabbitMQ
     * перенаправляет заказ в notification-service c PUSH статусом
     *
     * @param message сообщение с полученным новым заказом
     * в строковом представлении
     */
    @SneakyThrows
    @RabbitListener(queues = "OrdersNewForRestaurant")
    public void messageListenerContainer(String message) {
        ModelMessageOrder modelMessageOrder = objectMapper
                .readValue(message, ModelMessageOrder.class);
        log.info(modelMessageOrder.toString());

        ModelMessageOrder messageOrderToPush = objectMapper
                .readValue(message, ModelMessageOrder.class);
        messageOrderToPush.setType(MessageType.PUSH);
        messageOrderToPush.setAction(ActionType.NEW_ORDER_FOR_RESTAURANT);

        rabbitMQProducerService.sendPushToNotificationsExchange(
                mapModelMessageToString(messageOrderToPush));
    }


    //TODO вынести маппинг в отдельный класс
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
