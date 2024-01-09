package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
