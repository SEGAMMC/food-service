package ru.liga.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Courier;
import ru.liga.common.enums.CourierStatus;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findCouriersByStatus(CourierStatus status);
}
