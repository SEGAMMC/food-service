package ru.liga.deliveryservice.service.rabbit;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

public class RoutingMQCourierFactory {

    /**
     * Declarables - Класс объединящий в себе очереди, тип обменника и байдинги(связи)
     * Предназначен для создания очередей и привязки их к обменникам
     * при регистрации новых курьеров
     */
    public Declarables newCourier(String courierId) {
        Queue newCourier = new Queue(courierId, false);
        DirectExchange directExchange = new DirectExchange("CourierExchange");
        String routingKey = "push.courier." + courierId;

        return new Declarables(newCourier, directExchange,
                BindingBuilder.bind(newCourier).to(directExchange).with(routingKey));
    }
}
