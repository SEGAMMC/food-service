package ru.liga.courierservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.courierservice.dto.CourierUpdateRequest;
import ru.liga.courierservice.repository.CourierRepository;

/**
 * Сервис для работы с курьерами
 */
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    /**
     * Получение информации о курьере по ID
     *
     * @return
     */
    @Override
    public Courier getCourierById(long id) {
        return courierRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("error"));
    }

    /**
     * Изменение информации о курьере по ID
     */
    @Override
    public Courier updateCourier(CourierUpdateRequest updateCourier) {

    }





}
