package ru.liga.restaurantservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.common.enums.ActionType;
import ru.liga.common.enums.MessageType;
import ru.liga.restaurantservice.service.ListenerService;
import ru.liga.restaurantservice.service.RabbitMQProducerService;

@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final RabbitMQProducerService rabbitMQProducerService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "OrdersNewForRestaurant")
    public void messageListenerContainer(String message) {
        ModelMessageOrder modelMessageOrder = objectMapper
                .readValue(message, ModelMessageOrder.class);
        System.out.println(modelMessageOrder);

        ModelMessageOrder messageOrderToPush = objectMapper
                .readValue(message, ModelMessageOrder.class);
        messageOrderToPush.setType(MessageType.PUSH);
        messageOrderToPush.setAction(ActionType.NEW_ORDER_FOR_RESTAURANT);

        rabbitMQProducerService.sendPushToNotificationsExchange(
                mapModelMessageToString(messageOrderToPush));
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
            e.printStackTrace();
        }
        return messageModelToString;
    }
}
