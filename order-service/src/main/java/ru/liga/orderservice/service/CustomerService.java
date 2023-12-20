package ru.liga.orderservice.service;

import org.springframework.stereotype.Service;
import ru.liga.common.entity.Customer;
import ru.liga.orderservice.dto.request.CustomerStatusRequest;
import ru.liga.orderservice.dto.request.UpdateCustomerRequest;

@Service
public interface CustomerService {

    Customer getCustomerById(long id);

    void updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest );

    void updateCustomerStatus(long id, CustomerStatusRequest customerStatusRequest);

}
