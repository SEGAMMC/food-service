package ru.liga.restaurantservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.RestaurantStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantStatusRequest {
    private RestaurantStatus restaurantStatus;
}
