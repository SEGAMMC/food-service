package ru.liga.deliveryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CourierStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourierResponse {

    private String phone;

    private CourierStatus status;

    private String coordinates;

}
