package ru.liga.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CustomerStatus;
import ru.liga.common.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatusRequest {
    private CustomerStatus customerStatus;
}
