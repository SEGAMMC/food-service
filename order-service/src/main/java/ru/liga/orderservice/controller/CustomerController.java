package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.request.CustomerStatusRequest;
import ru.liga.orderservice.dto.request.UpdateCustomerRequest;
import ru.liga.orderservice.dto.response.CustomerResponse;
import ru.liga.orderservice.service.CustomerService;

/**
 * Контроллер для работы с клиентами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Получение информации о клиенте по ID
     *
     * @param id идентификационный номер клиента
     * @return возвращает информацию о клиенте
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable long id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Изменение информации о клиенте
     *
     * @param id                    идентификационный номер клиента
     * @param updateCustomerRequest новая информация о клиенте
     */
    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable long id,
                               @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        customerService.updateCustomer(id, updateCustomerRequest);
    }

    /**
     * Изменение статуса клиента
     *
     * @param id                    идентификационный номер клиента
     * @param customerStatusRequest новый статус клиента
     */
    @PutMapping("/{id}/status")
    public void updateCustomerStatus(@PathVariable long id,
                                     @RequestBody CustomerStatusRequest
                                             customerStatusRequest) {
        customerService.updateCustomerStatus(id, customerStatusRequest);
    }
}
