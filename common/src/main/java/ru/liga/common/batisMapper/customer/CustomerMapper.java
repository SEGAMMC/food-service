package ru.liga.common.batisMapper.customer;

import ru.liga.common.entity.Customer;

public interface CustomerMapper {

    Customer getCustomerById(Long id);

    Customer getCustomerByEmail(String email);

    void updateCustomerPhone(Customer Customer);

}
