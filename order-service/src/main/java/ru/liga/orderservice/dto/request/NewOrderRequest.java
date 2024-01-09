package ru.liga.orderservice.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOrderRequest {

    private long restaurantId;

    private List<OrderItemRequest> orderItems;

}
