package ru.liga.restaurantservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurantservice.dto.request.MenuItemRequest;
import ru.liga.restaurantservice.dto.request.UpdateItemStatusRequest;
import ru.liga.restaurantservice.dto.request.UpdatePriceMenuItemRequest;
import ru.liga.restaurantservice.dto.response.MenuItemResponse;
import ru.liga.restaurantservice.service.MenuItemService;

/**
 * Контроллер для работы с меню ресторана
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants/menuitems")
public class RestaurantMenuController {

    private final MenuItemService menuItemService;

    /**
     * Получение информации о блюде по его номеру
     *
     * @param itemId идентификационный номер блюда
     * @return информация о блюде
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable long itemId) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(itemId));
    }

    /**
     * Добавление нового блюда в меню ресторана
     *
     * @param newMenuItem параметры нового блюда
     */
    @PostMapping
    public ResponseEntity<MenuItemResponse> createNewMenuItem(
            @RequestBody MenuItemRequest newMenuItem) {
        return ResponseEntity.ok(menuItemService.createNewMenuItem(newMenuItem));
    }

    /**
     * Изменение информации о блюде в меню ресторана
     *
     * @param itemId         идентификационный номер блюда
     * @param updateMenuItem параметры измененного блюда
     */
    @PutMapping("/{itemId}")
    public void updateMenuItem(@PathVariable long itemId,
                               @RequestBody MenuItemRequest updateMenuItem) {
        menuItemService.updateMenuItem(itemId, updateMenuItem);
    }

    /**
     * Изменение цены блюда в меню ресторана
     *
     * @param itemId              идентификационный номер блюда
     * @param updatePriceMenuItem новая цена блюда
     */
    @PutMapping("/{itemId}/price")
    public void updatePriceItem(
            @PathVariable long itemId,
            @RequestBody UpdatePriceMenuItemRequest updatePriceMenuItem) {
        menuItemService.updatePriceMenuItem(itemId, updatePriceMenuItem);
    }

    /**
     * Изменение статуса блюда в меню ресторана
     *
     * @param itemId                  идентификационный номер блюда
     * @param updateItemStatusRequest новая статус блюда
     */
    @PutMapping("/{itemId}/status")
    public void updateItemStatus(
            @PathVariable long itemId,
            @RequestBody UpdateItemStatusRequest updateItemStatusRequest) {
        menuItemService.updateItemStatus(itemId, updateItemStatusRequest);
    }

    /**
     * Изменение статуса блюда в меню ресторана на Active
     *
     * @param itemId идентификационный номер блюда
     */
    @PutMapping("/{itemId}/status/active")
    public void updateItemStatusActive(@PathVariable long itemId) {
        menuItemService.updateItemStatusActive(itemId);
    }

    /**
     * Изменение статуса блюда в меню ресторана на Inactive
     *
     * @param itemId идентификационный номер блюда
     */
    @PutMapping("/{itemId}/status/inactive")
    public void updateItemStatusInactive(@PathVariable long itemId) {
        menuItemService.updateItemStatusInactive(itemId);
    }

    /**
     * Удаление блюда из меню ресторана
     *
     * @param itemId идентификационный номер
     */
    @DeleteMapping("/{itemId}")
    public void deleteMenuItem(@PathVariable long itemId) {
        menuItemService.deleteMenuItem(itemId);
    }

    //Feign methods
    //TODO возможно надо изменить и не обмениваться сущностями в открытом виде

    /**
     * Получение информации о блюде по его номеру для сервисов
     *
     * @param menuItemId идентификационный номер блюда
     * @return информация о блюде для ресторана
     */
    @GetMapping("/{menuItemId}/service")
    public MenuItemResponse getMenuItemByIdForService(@PathVariable long menuItemId) {
        return menuItemService.getMenuItemByIdForService(menuItemId);
    }

    /**
     * Получение списка информации о блюде из меню ресторана от restaurant-service
     *
     * @param listMenuItemId список идентификационных номеров блюд в меню
     * @return возвращает информацию о блюде из ресторана
     */
    @PostMapping("/list/service")
    public List<MenuItemResponse> getListMenuItemForService(
            @RequestBody List<Long> listMenuItemId) {
        return menuItemService.getListMenuItemForService(listMenuItemId);
    }
}
