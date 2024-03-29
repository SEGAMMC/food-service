package ru.liga.orderservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private UUID id;
    private RestaurantResponse restaurant;
    private LocalDateTime timestamp;
    private List<OrderItemResponse> items;
}
