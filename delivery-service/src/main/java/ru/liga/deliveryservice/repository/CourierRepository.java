package ru.liga.deliveryservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.entity.Courier;
import ru.liga.common.enums.CourierStatus;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findCouriersByStatus(CourierStatus status);
}
