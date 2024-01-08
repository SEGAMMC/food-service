package ru.liga.notificationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.notificationservice.service.ListenerService;
import ru.liga.notificationservice.service.PushClientService;

@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final PushClientService pushClientService;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "OrdersNewForNotification")
    public void messageListenerContainer(String message) {
        ModelMessageOrder modelMessageOrder = objectMapper.readValue(message, ModelMessageOrder.class);
        System.out.println(modelMessageOrder);
        pushClientService.distributeMessages(modelMessageOrder);
    }

}
