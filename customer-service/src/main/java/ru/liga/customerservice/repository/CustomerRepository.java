package ru.liga.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
