package ru.liga.deliveryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryOrderInfoResponse {

    private UUID orderId;

    private DeliveryRestaurantInfoResponse restaurant;

    private DeliveryCustomerInfoResponse customer;

    private BigDecimal payment;

}