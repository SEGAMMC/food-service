package ru.liga.deliveryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CourierStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCourierRequest {

    private String phone;

    private CourierStatus status;

    private String coordinates;

}

