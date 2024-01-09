package ru.liga.deliveryservice.service;

import java.util.UUID;
import ru.liga.common.entity.ModelMessageOrder;
import ru.liga.deliveryservice.dto.request.OrderStatusRequest;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;

public interface DeliveryService {
    void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus);

    DeliveriesResponse getOrdersDeliveryByStatus(long courierId, String status);

    void startDeliveryOrder(ModelMessageOrder modelMessageOrder);

    void courierTakeOrder(long courierId, UUID uuid);

    void courierPickUPOrder(long courierId, UUID uuid);

    void courierCompleteOrder(long courierId, UUID uuid);
}
