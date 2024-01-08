package ru.liga.deliveryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersByStatusAndCoordsRequest {

    private String coordX;

    private String coordY;

    private OrderStatus orderStatus;

}
