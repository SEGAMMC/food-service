package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.request.UpdateCourierRequest;
import ru.liga.deliveryservice.dto.response.CourierResponse;
import ru.liga.deliveryservice.service.CourierService;

/**
 * Контроллер для работы с курьерами
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierController {
	
	private final CourierService courierService;
	
	/**
	 * Получение информации о курьере
	 *
	 * @param courierId идентификационный номер курьера
	 * @return возвращает информацию о курьере
	 */
    @GetMapping("/{courierId}")
    public ResponseEntity<CourierResponse> getCourierById(
			@PathVariable long courierId) {
		return ResponseEntity.ok(courierService.getCourierById(courierId));
	}

	/**
	 * Изменение информации о курьере
	 *
	 * @param courierId      идентификационный номер курьера
	 * @param courierRequest новая информация о курьере
	 */
    @PutMapping("/{courierId}")
    public void updateCourierInfo(@PathVariable long courierId
			, @RequestBody UpdateCourierRequest courierRequest) {
		courierService.updateCourierInfo(courierId, courierRequest);
    }
}