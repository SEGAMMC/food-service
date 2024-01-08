package ru.liga.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CourierStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourierUpdateRequest {

    private String phone;

    private CourierStatus status;

    private String coordinates;

}