package ru.liga.orderservice.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderExchangeRequest {
    private String queue;

    private String routingKey;

    private UUID orderId;

}
