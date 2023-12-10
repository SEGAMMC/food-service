package ru.liga.courierservice.service;

import ru.liga.common.entity.Courier;
import ru.liga.courierservice.dto.CourierUpdateRequest;

public interface CourierService {

    Courier getCourierById(long id);

    void updateCourier();

    void updateCourier(CourierUpdateRequest updateCourier);
}
