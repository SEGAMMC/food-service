package ru.liga.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemForRestaurantResponse {

    private long orderItemId;

    private MenuItemForListResponse menuItem;

    private int quantity;

}
