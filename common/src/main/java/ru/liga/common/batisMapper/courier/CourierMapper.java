package ru.liga.common.batisMapper.courier;

import ru.liga.common.entity.Courier;

public interface CourierMapper {
    Courier getCourierById(Long id);

    Courier getCourierByPhone(String phone);

    void updateCourierStatus(Courier courier);
}
