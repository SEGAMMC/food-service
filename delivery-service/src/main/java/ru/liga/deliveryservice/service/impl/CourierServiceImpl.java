package ru.liga.deliveryservice.service.impl;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.deliveryservice.dto.request.UpdateCourierRequest;
import ru.liga.deliveryservice.dto.response.CourierResponse;
import ru.liga.deliveryservice.repository.CourierRepository;
import ru.liga.deliveryservice.service.CourierService;

/**
 * Сервис для работы с курьерами
 */
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    /**
     * Получение информации о курьере
     *
     * @param courierId идентификационный номер курьера
     * @return возвращает информацию о курьере
     */
    @Override
    public CourierResponse getCourierById(long courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));
        return mapCourierToCourierResponse(courier);
    }

    /**
     * Изменение информации о курьере
     *
     * @param courierId      идентификационный номер курьера
     * @param courierRequest новая информация о курьере
     */
    @Override
    public void updateCourierInfo(long courierId,
                                  UpdateCourierRequest courierRequest) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));

        courier.setPhone(courierRequest.getPhone());
        courier.setStatus(courierRequest.getStatus());
        courier.setCoordinates(courierRequest.getCoordinates());
        courierRepository.save(courier);
    }

    private CourierResponse mapCourierToCourierResponse(Courier courier) {
        return CourierResponse.builder()
                .phone(courier.getPhone())
                .status(courier.getStatus())
                .coordinates(courier.getCoordinates())
                .build();
    }
}
