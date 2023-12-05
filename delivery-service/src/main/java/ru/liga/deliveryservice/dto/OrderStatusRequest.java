package ru.liga.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.entity.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {
    private OrderStatus status;
}
