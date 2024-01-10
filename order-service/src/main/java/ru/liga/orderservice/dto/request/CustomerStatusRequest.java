package ru.liga.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CustomerStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatusRequest {
    private CustomerStatus customerStatus;
}
