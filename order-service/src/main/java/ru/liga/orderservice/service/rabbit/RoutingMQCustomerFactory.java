package ru.liga.orderservice.service.rabbit;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;


/**
 * Сервис для создания новых очередей(клиентов) в RabbitMQ
 */
public class RoutingMQCustomerFactory {

    /**
     * Declarables - Класс объединящий в себе очереди, тип обменника и байдинги(связи)
     * Предназначен для создания очередей и привязки их к обменникам
     * при регистрации новых клиентов
     */
    public Declarables newCustomer(String customerId) {
        Queue newCustomer = new Queue("Customer" + customerId, false);
        DirectExchange directExchange = new DirectExchange("NotificationsExchange");

        return new Declarables(newCustomer, directExchange, BindingBuilder.bind(newCustomer)
                .to(directExchange).with("push.customer." + customerId));
    }

}
