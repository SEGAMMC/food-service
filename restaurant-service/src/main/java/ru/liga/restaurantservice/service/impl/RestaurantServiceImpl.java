package ru.liga.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.common.enums.RestaurantStatus;
import ru.liga.restaurantservice.dto.request.RestaurantStatusRequest;
import ru.liga.restaurantservice.dto.request.RestaurantUpdateInfoRequest;
import ru.liga.restaurantservice.dto.response.MenuItemForListResponse;
import ru.liga.restaurantservice.dto.response.RestaurantResponse;
import ru.liga.restaurantservice.handler.NoSuchElementException;
import ru.liga.restaurantservice.repository.MenuItemRepository;
import ru.liga.restaurantservice.repository.RestaurantRepository;
import ru.liga.restaurantservice.service.RestaurantService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с ресторанами
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    /**
     * Получение информации о ресторане по его номеру
     *
     * @param restaurantId - идентификационный номер ресторана
     * @return информация о ресторане
     */
    public RestaurantResponse getRestaurantById(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        return mapRestaurantToRestaurantResponse(restaurant);
    }

    /**
     * Изменение информации о ресторане
     *
     * @param restaurantId   - идентификационный номер ресторана
     * @param restaurantInfo - новая информация о ресторане
     */
    public void updateRestaurantById(long restaurantId
            , RestaurantUpdateInfoRequest restaurantInfo) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        Restaurant mapRestaurant = mapRestaurantInfoToRestaurant(restaurantInfo);
        mapRestaurant.setId(restaurant.getId());
        restaurantRepository.save(mapRestaurant);
    }

    /**
     * Изменение статуса ресторана
     *
     * @param restaurantId     - идентификационный номер ресторана
     * @param restaurantStatus - новый статус ресторана
     */
    public void updateRestaurantStatus(long restaurantId
            , RestaurantStatusRequest restaurantStatus) {
        updateRestaurantStatus(restaurantId, restaurantStatus.getRestaurantStatus());
    }

    /**
     * Изменение статуса ресторана на ACTIVE
     *
     *@param restaurantId - идентификационный номер ресторана
     */
    @Override
    public void updateRestaurantStatusActive(long restaurantId) {
        updateRestaurantStatus(restaurantId, RestaurantStatus.ACTIVE);
    }

    /**
     * Изменение статуса ресторана на INACTIVE
     *
     *@param restaurantId - идентификационный номер ресторана
     */
    @Override
    public void updateRestaurantStatusInactive(long restaurantId) {
        updateRestaurantStatus(restaurantId, RestaurantStatus.INACTIVE);
    }

    /**
     * Удаление ресторана из БД
     *
     * @param restaurantId - идентификационный номер ресторана
     */
    public void deleteRestaurant(long restaurantId) {
        updateRestaurantStatus(restaurantId, RestaurantStatus.DELETE);
    }

    /**
     * Получение списка блюд конкретного ресторана
     *
     * @param restaurantId - идентификационный номер ресторана
     * @return список блюд конкретного ресторана
     */
    public List<MenuItemForListResponse> getMenuItemsRestaurant(long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));

        List<RestaurantMenuItem> restaurantMenuItems = menuItemRepository
                .getRestaurantMenuItemsByRestaurantId(restaurant);

        List<MenuItemForListResponse> menuItemForListResponses = new ArrayList<>();

        for (RestaurantMenuItem menuItem : restaurantMenuItems) {
            MenuItemForListResponse menuItemForList = new MenuItemForListResponse();
            menuItemForList = menuItemForList.builder()
                    .id(menuItem.getId())
                    .name(menuItem.getName())
                    .description(menuItem.getDescription())
                    .price(menuItem.getPrice())
                    .image_url(menuItem.getImage_url())
                    .build();
            menuItemForListResponses.add(menuItemForList);
        }
        return menuItemForListResponses;
    }

    /**
     * Изменение статуса ресторана (основной метод)
     *
     * @param restaurantId     - идентификационный номер ресторана
     * @param restaurantStatus - новый статус ресторана
     */
    private void updateRestaurantStatus(long restaurantId
            , RestaurantStatus restaurantStatus) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение2"));
        restaurant.setStatus(restaurantStatus);
        restaurantRepository.save(restaurant);
    }

    private RestaurantResponse mapRestaurantToRestaurantResponse(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        return restaurantResponse.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .status(restaurant.getStatus())
                .build();
    }

    private Restaurant mapRestaurantInfoToRestaurant(RestaurantUpdateInfoRequest restaurantInfo) {
        Restaurant restaurant = new Restaurant();
        return restaurant.builder()
                .address(restaurantInfo.getAddress())
                .name(restaurantInfo.getName())
                .status(restaurantInfo.getStatus())
                .build();
    }
}