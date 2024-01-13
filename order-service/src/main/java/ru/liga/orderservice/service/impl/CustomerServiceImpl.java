package ru.liga.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Customer;
import ru.liga.orderservice.dto.request.CustomerStatusRequest;
import ru.liga.orderservice.dto.request.UpdateCustomerRequest;
import ru.liga.orderservice.dto.response.CustomerResponse;
import ru.liga.orderservice.handler.NoSuchElementException;
import ru.liga.orderservice.repository.CustomerRepository;
import ru.liga.orderservice.service.CustomerService;

/**
 * Сервис для работы с клиентами (Customers)
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Получение информации о клиенте по ID
     *
     * @param id идентификационный номер клиента
     * @return возвращает информацию о клиенте
     */
    @Override
    public CustomerResponse getCustomerById(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));
        //todo убрать маппинг в отдельный файл
        return mapCustomerToCustomerResponse(customer);
    }

    /**
     * Изменение информации о клиенте
     *
     * @param id                    идентификационный номер клиента
     * @param updateCustomerRequest новая информация о клиенте
     */
    @Override
    public void updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest) {
        //TODO VALID request

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        customer.setPhone(updateCustomerRequest.getPhone());
        customer.setStatus(updateCustomerRequest.getStatus());
        customer.setEmail(updateCustomerRequest.getEmail());
        customer.setAddress(updateCustomerRequest.getAddress());
        customerRepository.save(customer);
    }

    /**
     * Изменение статуса клиента
     *
     * @param id                    идентификационный номер клиента
     * @param customerStatusRequest новый статус клиента
     */
    @Override
    public void updateCustomerStatus(long id,
                                     CustomerStatusRequest customerStatusRequest) {
        //TODO VALID request и преобразовать  в enum

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Написать текст"));

        customer.setStatus(customerStatusRequest.getCustomerStatus());
        customerRepository.save(customer);
    }

    private CustomerResponse mapCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .status(customer.getStatus())
                .build();
    }
}
