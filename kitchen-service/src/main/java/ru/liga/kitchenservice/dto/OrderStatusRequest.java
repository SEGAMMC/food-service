package ru.liga.kitchenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {
    private OrderStatus status;
}
