package ru.liga.orderservice.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {

    /**
     * Declarables - Класс объединящий в себе очереди, тип обменника и байдинги(связи)
     * Предназначен для создания очередей и привязки их к обменникам
     * при регистрации новых клиентов
     * <p>
     * Создание всех необходимых обменников, очередей и routingkey
     */
    @Bean
    public Declarables myExchanges() {
        DirectExchange notificationExchange = new DirectExchange("NotificationsExchange");
        DirectExchange deliveriesExchange = new DirectExchange("DeliveriesExchange");
        DirectExchange restaurantsExchange = new DirectExchange("RestaurantsExchange");

        Queue ordersNewForNotification = new Queue("OrdersNewForNotification", false);
        Queue ordersNewForDelivery = new Queue("OrdersNewForDelivery", false);
        Queue ordersNewForRestaurant = new Queue("OrdersNewForRestaurant", false);

        Queue restaurant1 = new Queue("Restaurant1", false);
        Queue restaurant2 = new Queue("Restaurant2", false);
        Queue restaurant3 = new Queue("Restaurant3", false);
        Queue restaurant4 = new Queue("Restaurant4", false);
        Queue restaurant5 = new Queue("Restaurant5", false);
        Queue restaurant6 = new Queue("Restaurant6", false);
        Queue restaurant7 = new Queue("Restaurant7", false);
        Queue restaurant8 = new Queue("Restaurant8", false);
        Queue restaurant9 = new Queue("Restaurant9", false);
        Queue restaurant10 = new Queue("Restaurant10", false);

        Queue courier1 = new Queue("Courier1", false);
        Queue courier2 = new Queue("Courier2", false);
        Queue courier3 = new Queue("Courier3", false);
        Queue courier4 = new Queue("Courier4", false);
        Queue courier5 = new Queue("Courier5", false);
        Queue courier6 = new Queue("Courier6", false);
        Queue courier7 = new Queue("Courier7", false);
        Queue courier8 = new Queue("Courier8", false);
        Queue courier9 = new Queue("Courier9", false);
        Queue courier10 = new Queue("Courier10", false);

        Queue customer1 = new Queue("Customer1", false);
        Queue customer2 = new Queue("Customer2", false);
        Queue customer3 = new Queue("Customer3", false);
        Queue customer4 = new Queue("Customer4", false);
        Queue customer5 = new Queue("Customer5", false);
        Queue customer6 = new Queue("Customer6", false);
        Queue customer7 = new Queue("Customer7", false);
        Queue customer8 = new Queue("Customer8", false);
        Queue customer9 = new Queue("Customer9", false);
        Queue customer10 = new Queue("Customer10", false);

        return new Declarables(
                notificationExchange, deliveriesExchange, restaurantsExchange,
                ordersNewForNotification, ordersNewForDelivery, ordersNewForRestaurant,
                BindingBuilder.bind(ordersNewForNotification).to(notificationExchange).with("push.new"),
                BindingBuilder.bind(ordersNewForDelivery).to(deliveriesExchange).with("order.delivery.new"),
                BindingBuilder.bind(ordersNewForRestaurant).to(restaurantsExchange).with("order.restaurant.new"),

                restaurant1, restaurant2, restaurant3, restaurant4, restaurant5, restaurant6, restaurant7, restaurant8, restaurant9, restaurant10,
                BindingBuilder.bind(restaurant1).to(notificationExchange).with("push.restaurant.1"),
                BindingBuilder.bind(restaurant2).to(notificationExchange).with("push.restaurant.2"),
                BindingBuilder.bind(restaurant3).to(notificationExchange).with("push.restaurant.3"),
                BindingBuilder.bind(restaurant4).to(notificationExchange).with("push.restaurant.4"),
                BindingBuilder.bind(restaurant5).to(notificationExchange).with("push.restaurant.5"),
                BindingBuilder.bind(restaurant6).to(notificationExchange).with("push.restaurant.6"),
                BindingBuilder.bind(restaurant7).to(notificationExchange).with("push.restaurant.7"),
                BindingBuilder.bind(restaurant8).to(notificationExchange).with("push.restaurant.8"),
                BindingBuilder.bind(restaurant9).to(notificationExchange).with("push.restaurant.9"),
                BindingBuilder.bind(restaurant10).to(notificationExchange).with("push.restaurant.10"),

                courier1, courier2, courier3, courier4, courier5, courier6, courier7, courier8, courier9, courier10,
                BindingBuilder.bind(courier1).to(notificationExchange).with("push.courier.1"),
                BindingBuilder.bind(courier2).to(notificationExchange).with("push.courier.2"),
                BindingBuilder.bind(courier3).to(notificationExchange).with("push.courier.3"),
                BindingBuilder.bind(courier4).to(notificationExchange).with("push.courier.4"),
                BindingBuilder.bind(courier5).to(notificationExchange).with("push.courier.5"),
                BindingBuilder.bind(courier6).to(notificationExchange).with("push.courier.6"),
                BindingBuilder.bind(courier7).to(notificationExchange).with("push.courier.7"),
                BindingBuilder.bind(courier8).to(notificationExchange).with("push.courier.8"),
                BindingBuilder.bind(courier9).to(notificationExchange).with("push.courier.9"),
                BindingBuilder.bind(courier10).to(notificationExchange).with("push.courier.10"),

                customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10,
                BindingBuilder.bind(customer1).to(notificationExchange).with("push.customer.1"),
                BindingBuilder.bind(customer2).to(notificationExchange).with("push.customer.2"),
                BindingBuilder.bind(customer3).to(notificationExchange).with("push.customer.3"),
                BindingBuilder.bind(customer4).to(notificationExchange).with("push.customer.4"),
                BindingBuilder.bind(customer5).to(notificationExchange).with("push.customer.5"),
                BindingBuilder.bind(customer6).to(notificationExchange).with("push.customer.6"),
                BindingBuilder.bind(customer7).to(notificationExchange).with("push.customer.7"),
                BindingBuilder.bind(customer8).to(notificationExchange).with("push.customer.8"),
                BindingBuilder.bind(customer9).to(notificationExchange).with("push.customer.9"),
                BindingBuilder.bind(customer10).to(notificationExchange).with("push.customer.10")
        );
    }
}
