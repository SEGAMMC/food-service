package ru.liga.restaurantservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.common.enums.MenuItemStatus;
import ru.liga.restaurantservice.dto.request.MenuItemRequest;
import ru.liga.restaurantservice.dto.request.UpdateItemStatusRequest;
import ru.liga.restaurantservice.dto.request.UpdatePriceMenuItemRequest;
import ru.liga.restaurantservice.dto.response.MenuItemResponse;
import ru.liga.restaurantservice.handler.NoSuchElementException;
import ru.liga.restaurantservice.repository.MenuItemRepository;
import ru.liga.restaurantservice.repository.RestaurantRepository;
import ru.liga.restaurantservice.service.MenuItemService;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * Получение информации о блюде по его номеру
     *
     * @param itemId идентификационный номер блюда
     * @return информация о блюде
     */
    @Override
    public MenuItemResponse getMenuItemById(long itemId) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        return mapMenuItemToMenuItemResponse(menuItem);
    }

    /**
     * Добавление нового блюда в меню ресторана
     *
     * @param newMenuItem параметры нового блюда
     * @return информация о новом блюде
     */
    @Override
    public MenuItemResponse createNewMenuItem(MenuItemRequest newMenuItem) {
        RestaurantMenuItem menuItem = mapMenuItemRequestToMenuItem(newMenuItem);
        menuItem = menuItemRepository.save(menuItem);
        return mapMenuItemToMenuItemResponse(menuItem);
    }

    /**
     * Изменение информации о блюде в меню ресторана
     *
     * @param itemId         идентификационный номер блюда
     * @param updateMenuItem параметры измененного блюда
     */
    @Override
    public void updateMenuItem(long itemId, MenuItemRequest updateMenuItem) {
        RestaurantMenuItem menuItem = mapMenuItemRequestToMenuItem(updateMenuItem);
        menuItem.setId(itemId);
        menuItemRepository.save(menuItem);
    }

    /**
     * Изменение цены блюда в меню ресторана
     *
     * @param itemId              идентификационный номер блюда
     * @param updatePriceMenuItem новая цена блюда
     */
    @Override
    public void updatePriceMenuItem(long itemId,
                                    UpdatePriceMenuItemRequest updatePriceMenuItem) {
        //TODO ввести проверку  запроса
        RestaurantMenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        menuItem.setPrice(updatePriceMenuItem.getPrice());
        menuItemRepository.save(menuItem);
    }

    /**
     * Изменение статуса блюда в меню ресторана
     *
     * @param itemId                  идентификационный номер блюда
     * @param updateItemStatusRequest новый статус блюда
     */
    @Override
    public void updateItemStatus(long itemId,
                                 UpdateItemStatusRequest updateItemStatusRequest) {
        updateItemStatus(itemId, updateItemStatusRequest.getStatus());
    }

    /**
     * Изменение статуса блюда в меню ресторана на Active
     *
     * @param itemId идентификационный номер блюда
     */
    @Override
    public void updateItemStatusActive(long itemId) {
        updateItemStatus(itemId, MenuItemStatus.ACTIVE);
    }

    /**
     * Изменение статуса блюда в меню ресторана на Inactive
     *
     * @param itemId идентификационный номер блюда
     */
    @Override
    public void updateItemStatusInactive(long itemId) {
        updateItemStatus(itemId, MenuItemStatus.INACTIVE);
    }

    /**
     * Удаление блюда из меню ресторана
     *
     * @param itemId идентификационный номер
     */

    @Override
    public void deleteMenuItem(long itemId) {
        updateItemStatus(itemId, MenuItemStatus.INACTIVE);
    }


    /**
     * Изменение статуса блюда в меню ресторана
     *
     * @param itemId     идентификационный номер блюда
     * @param itemStatus новый статус блюда
     */
    private void updateItemStatus(long itemId, MenuItemStatus itemStatus) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        menuItem.setStatus(itemStatus);
        menuItemRepository.save(menuItem);
    }

    private MenuItemResponse mapMenuItemToMenuItemResponse(RestaurantMenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .restaurantId(menuItem.getRestaurantId().getId())
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .imageUrl(menuItem.getImageUrl())
                .description(menuItem.getDescription())
                .status(menuItem.getStatus())
                .build();
    }

    private RestaurantMenuItem mapMenuItemRequestToMenuItem(MenuItemRequest newMenuItem) {
        Restaurant restaurant = restaurantRepository.findById(
                        newMenuItem.getRestaurantId())
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        return RestaurantMenuItem.builder()
                .restaurantId(restaurant)
                .name(newMenuItem.getName())
                .price(newMenuItem.getPrice())
                .imageUrl(newMenuItem.getImageUrl())
                .description(newMenuItem.getDescription())
                .status(newMenuItem.getStatus())
                .build();
    }

    //Feign methods

    /**
     * Получение информации о блюде по его номеру для сервисов
     *
     * @param menuItemId идентификационный номер блюда
     * @return информация о блюде для ресторана
     */
    @Override
    public RestaurantMenuItem getMenuItemByIdForService(long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
    }

    /**
     * Получение списка информации о блюде из меню ресторана от restaurant-service
     *
     * @param listMenuItemId список идентификационных номеров блюд в меню
     * @return возвращает информацию о блюде из ресторана
     */
    @Override
    public List<RestaurantMenuItem> getListMenuItemForService(List<Long> listMenuItemId) {
        List<RestaurantMenuItem> listItem = new ArrayList<>();
        for (Long menuItems : listMenuItemId) {
            RestaurantMenuItem rmi = menuItemRepository.findById(menuItems)
                    .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
            listItem.add(rmi);
        }
        return listItem;
    }
}
