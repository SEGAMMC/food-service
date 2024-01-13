package ru.liga.restaurantservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.RestaurantStatusRequest;
import ru.liga.restaurantservice.dto.request.RestaurantUpdateInfoRequest;
import ru.liga.restaurantservice.dto.response.MenuItemForListResponse;
import ru.liga.restaurantservice.dto.response.RestaurantResponse;
import ru.liga.restaurantservice.service.RestaurantService;

/**
 * Контроллер для работы с ресторанами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * Получение информации о ресторане по его номеру
     *
     * @param restaurantId идентификационный номер ресторана
     * @return информация о ресторане
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    /**
     * Изменение информации о ресторане
     *
     * @param restaurantId   идентификационный номер ресторана
     * @param restaurantInfo новая информация о ресторане
     */
    @PutMapping("/{restaurantId}")
    public void updateRestaurant(
            @PathVariable long restaurantId,
            @RequestBody RestaurantUpdateInfoRequest restaurantInfo) {
        restaurantService.updateRestaurantById(restaurantId, restaurantInfo);
    }

    /**
     * Изменение статуса ресторана (uuid+JSON)
     *
     * @param restaurantId     идентификационный номер ресторана
     * @param restaurantStatus новый статус ресторана
     */
    @PutMapping("/{restaurantId}/status")
    public void updateRestaurantStatus(
            @PathVariable long restaurantId,
            @RequestBody RestaurantStatusRequest restaurantStatus) {
        restaurantService.updateRestaurantStatus(restaurantId, restaurantStatus);
    }

    /**
     * Изменение статуса ресторана на ACTIVE
     *
     * @param restaurantId идентификационный номер ресторана
     */
    @PutMapping("/{restaurantId}/status/active")
    public void updateRestaurantStatusActive(@PathVariable long restaurantId) {
        restaurantService.updateRestaurantStatusActive(restaurantId);
    }

    /**
     * Изменение статуса ресторана на INACTIVE
     *
     * @param restaurantId идентификационный номер ресторана
     */
    @PutMapping("/{restaurantId}/status/inactive")
    public void updateRestaurantStatusInactive(@PathVariable long restaurantId) {
        restaurantService.updateRestaurantStatusInactive(restaurantId);
    }

    /**
     * Удаление ресторана из БД
     *
     * @param restaurantId идентификационный номер ресторана
     */
    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@PathVariable long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }

    /**
     * Получение списка блюд конкретного ресторана
     *
     * @param restaurantId идентификационный номер ресторана
     * @return список блюд конкретного ресторана
     */
    @GetMapping("/{restaurantId}/menuitems")
    public ResponseEntity<List<MenuItemForListResponse>>
    getMenuItemAll(@PathVariable long restaurantId) {
        return ResponseEntity.ok(restaurantService
                .getMenuItemsRestaurant(restaurantId));
    }

}
