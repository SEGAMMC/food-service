package ru.liga.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfo {

    private UUID orderId;

    private String phoneCustomer;

    private String customerAddress;

    private String restaurantName;

    private String restaurantAddress;

    private LocalDateTime deliveryTime;

}