package ru.liga.notificationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.notificationservice.service.ListenerService;
import ru.liga.notificationservice.service.PushClientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final PushClientService pushClientService;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "OrdersNewForNotification")
    public void messageListenerContainer(String message) {
        ModelMessageOrder modelMessageOrder = objectMapper.readValue(message,
                ModelMessageOrder.class);
        log.info(modelMessageOrder.toString());
        pushClientService.distributeMessages(modelMessageOrder);
    }

}
