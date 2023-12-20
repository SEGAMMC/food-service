package ru.liga.deliveryservice.service;

import ru.liga.deliveryservice.dto.request.UpdateCourierRequest;
import ru.liga.deliveryservice.dto.response.CourierResponse;

public interface CourierService {

	CourierResponse getCourierById(long courierId);

	void updateCourierInfo(long courierId, UpdateCourierRequest courierRequest);

}
