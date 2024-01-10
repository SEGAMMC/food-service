package ru.liga.orderservice.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentResponse {
    private UUID id;
    private String secretPaymentUrl;
    private LocalDateTime estimatedTimeOfArrival;
}
