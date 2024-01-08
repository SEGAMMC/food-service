package ru.liga.restaurantservice.service;

import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.restaurantservice.dto.request.MenuItemRequest;
import ru.liga.restaurantservice.dto.request.UpdateItemStatusRequest;
import ru.liga.restaurantservice.dto.request.UpdatePriceMenuItemRequest;
import ru.liga.restaurantservice.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    MenuItemResponse getMenuItemById(long id);

    MenuItemResponse createNewMenuItem(MenuItemRequest newMenuItem);

    void updateMenuItem(long id, MenuItemRequest updateMenuItem);

    void updatePriceMenuItem(long id, UpdatePriceMenuItemRequest updatePriceMenuItem);

    void updateItemStatus(long itemId, UpdateItemStatusRequest updateItemStatusRequest);

    void updateItemStatusActive(long itemId);

    void updateItemStatusInactive(long itemId);

    void deleteMenuItem(long id);

    RestaurantMenuItem getMenuItemByIdForService(long menuItemId);

    List<RestaurantMenuItem> getListMenuItemForService(List<Long> listMenuItemId);
}