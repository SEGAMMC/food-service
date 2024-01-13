package ru.liga.orderservice.service;

import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.request.CustomerStatusRequest;
import ru.liga.orderservice.dto.request.UpdateCustomerRequest;
import ru.liga.orderservice.dto.response.CustomerResponse;

@Service
public interface CustomerService {

    CustomerResponse getCustomerById(long id);

    void updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest);

    void updateCustomerStatus(long id, CustomerStatusRequest customerStatusRequest);

}
