package ru.liga.restaurantservice.feign_core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.restaurantservice.dto.request.DeliveryExchangeRequest;
import ru.liga.restaurantservice.dto.request.PushRequest;

@FeignClient(name = "notification-service", url = "http://localhost:8083/api/v1/notifications")
public interface FeignToNotification {

    /**
     * Отправка нового заказа в очередь Delivery Exchange RabbitMQ
     *
     * @param order информация по заказу необходимая для передачи в delivery service
     * @return возвращает true при успешной отправки сообщения в очередь
     */
    @PostMapping("/deliveries")
    boolean sendOrderToDeliveryExchange(@RequestBody DeliveryExchangeRequest order);


    /**
     * Отправка push-уведомления ресторану
     *
     * @param push сообщение push-уведомления
     * @return возвращает true в случае успешного завершения операции
     */
    @PostMapping("/push/restaurants")
    boolean sendPushToRestaurantExchange(@RequestBody PushRequest push);

}

