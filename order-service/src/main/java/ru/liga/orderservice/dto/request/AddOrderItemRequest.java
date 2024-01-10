package ru.liga.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderItemRequest {

    private long restaurantMenuItem;

    private int quantity;

}
