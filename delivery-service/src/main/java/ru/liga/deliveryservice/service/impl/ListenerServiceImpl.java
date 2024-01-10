package ru.liga.deliveryservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.deliveryservice.service.DeliveryService;
import ru.liga.deliveryservice.service.ListenerService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final DeliveryService deliveryService;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "OrdersNewForDelivery")
    public void messageListenerContainer(String message) {
        ModelMessageOrder modelMessageOrder = objectMapper.readValue(message,
                ModelMessageOrder.class);
        log.info(modelMessageOrder.toString());
        deliveryService.startDeliveryOrder(modelMessageOrder);
    }
}
