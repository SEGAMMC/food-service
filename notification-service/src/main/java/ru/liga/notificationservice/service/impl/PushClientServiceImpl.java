package ru.liga.notificationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.common.enums.ActionType;
import ru.liga.notificationservice.service.PushClientService;

import java.util.List;

/**
 * Сервис для отправки push уведомления на сторону клиента через RabbitMQ
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushClientServiceImpl implements PushClientService {

    private final String notificationExchange = "NotificationsExchange";

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    /**
     * Распределение сообщений в зависимости от типа действия ActionType
     *
     * @param modelMessage сообщение push-уведомления
     */
    @Override
    public void distributeMessages(ModelMessageOrder modelMessage) {
        ActionType actionType = modelMessage.getAction();
        List<ActionType> actionStatusForCustomer = List.of(
                ActionType.ORDER_CUSTOMER_CREATED,
                ActionType.ORDER_CUSTOMER_CANCELLED, ActionType.ORDER_DELIVERY_PENDING,
                ActionType.ORDER_DELIVERY_PICKING, ActionType.ORDER_DELIVERY_DENIED,
                ActionType.ORDER_DELIVERY_REFUNDED);

        List<ActionType> actionStatusForCustomerAndRestaurant = List.of(
                ActionType.ORDER_CUSTOMER_PAID_AND_CANCELLED,
                ActionType.ORDER_CUSTOMER_PAID, ActionType.ORDER_KITCHEN_ACCEPTED,
                ActionType.ORDER_KITCHEN_PREPARING, ActionType.ORDER_KITCHEN_DENIED,
                ActionType.ORDER_KITCHEN_REFUNDED);

        List<ActionType> actionStatusForCustomerAndCourier = List.of(
                ActionType.ORDER_DELIVERY_DELIVERING,
                ActionType.ORDER_DELIVERY_COMPLETE, ActionType.ADD_COURIER);

        if (actionStatusForCustomer.contains(actionType)) {
            sendPushToCustomer(modelMessage);
        }
        if (actionStatusForCustomerAndRestaurant.contains(actionType)) {
            sendPushToCustomer(modelMessage);
            sendPushToRestaurant(modelMessage);
        }
        if (actionStatusForCustomerAndCourier.contains(actionType)) {
            sendPushToCustomer(modelMessage);
            sendPushToCourier(modelMessage);
        }
        if (actionType.equals(ActionType.FIND_COURIER)) {
            sendPushToCourier(modelMessage);
        }
        if (actionType.equals(ActionType.NEW_ORDER_FOR_RESTAURANT)) {
            sendPushToRestaurant(modelMessage);
        }
    }

    /**
     * Отправка push-уведомления клиенту
     *
     * @param model сообщение push-уведомления
     * @return возвращает true в случае успешного завершения операции
     */
    @Override
    public boolean sendPushToCustomer(ModelMessageOrder model) {
        String routingKey = "push.customer." + model.getCustomerId();
        String modelInString = mapModelMessageToString(model);
        rabbitTemplate.convertAndSend(notificationExchange, routingKey, modelInString);
        return false;
    }

    /**
     * Отправка push-уведомления ресторану
     *
     * @param model сообщение push-уведомления
     * @return возвращает true в случае успешного завершения операции
     */
    @Override
    public boolean sendPushToRestaurant(ModelMessageOrder model) {
        String routingKey = "push.restaurant." + model.getRestaurantId();
        String modelInString = mapModelMessageToString(model);
        rabbitTemplate.convertAndSend(notificationExchange, routingKey, modelInString);
        return false;
    }

    /**
     * Отправка push-уведомления курьеру
     *
     * @param model сообщение push-уведомления
     * @return возвращает true в случае успешного завершения операции
     */
    @Override
    public boolean sendPushToCourier(ModelMessageOrder model) {
        String routingKey = "push.courier." + model.getCourierId();
        String modelInString = mapModelMessageToString(model);
        rabbitTemplate.convertAndSend(notificationExchange, routingKey, modelInString);
        return false;
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
            log.info(e.getMessage());
        }
        return messageModelToString;
    }

}
