package ru.liga.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentResponse {
    private UUID id;
    private String secretPaymentUrl;
    private String estimatedTimeOfArrival;
}