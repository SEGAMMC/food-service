package ru.liga.restaurantservice.service.rabbit;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

public class RoutingMQRestaurantFactory {

    /**
     * Declarables - Класс объединящий в себе очереди, тип обменника и байдинги(связи)
     * Предназначен для создания очередей и привязки их к обменникам
     * при регистрации новых ресторанов
     */
    public Declarables newRestaurant(String restaurantId) {
        Queue newRestaurant = new Queue(restaurantId, false);
        DirectExchange directExchange = new DirectExchange("NotificationsExchange");
        String routingKey = "push.restaurant." + restaurantId;

        return new Declarables(newRestaurant, directExchange, BindingBuilder.bind(newRestaurant).to(directExchange).with(routingKey));
    }
}
