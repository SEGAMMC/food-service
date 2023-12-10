package ru.liga.courierservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
