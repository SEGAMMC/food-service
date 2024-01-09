package ru.liga.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.CustomerStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String phone;

    private String email;

    private String address;

    private CustomerStatus status;

}
