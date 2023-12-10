package ru.liga.orderservice.dto.request;

import ru.liga.common.enums.RestaurantStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantStatusRequest {
    private RestaurantStatus restaurantStatus;
}
