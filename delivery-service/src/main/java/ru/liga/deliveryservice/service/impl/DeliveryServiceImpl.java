package ru.liga.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.Courier;
import ru.liga.common.entity.Customer;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.enums.OrderStatus;
import ru.liga.deliveryservice.dto.OrderStatusRequest;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;
import ru.liga.deliveryservice.dto.response.DeliveryCustomerInfoResponse;
import ru.liga.deliveryservice.dto.response.DeliveryOrderInfoResponse;
import ru.liga.deliveryservice.dto.response.DeliveryRestaurantInfoResponse;
import ru.liga.deliveryservice.repository.CourierRepository;
import ru.liga.deliveryservice.repository.OrderRepository;
import ru.liga.deliveryservice.service.DeliveryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Сервис для работы курьеров с заказами
 */
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final double EARTH_RADIUS = 6372795; //радиус земли в метрах

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;


    /**
     * Обновление статуса заказа
     */
    @Override
    public void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus) {

    }

    /**
     * Получение списка доступных заказов для доставки с соответствующим
     * статусом заказа
     *
     * @param status - статус заказа
     * @return возвращает список заказов на доставку с соответствиющим статусом
     */
    @Override
    public DeliveriesResponse getOrdersDeliveryByStatus(long courierId, String status) {
        String strStatus = "DELIVERY_" + status.toUpperCase();
        OrderStatus orderStatus = checkStatus(strStatus);

        List<Order> orderList = orderRepository.getOrdersByStatus(orderStatus);


        return mapOrderListToDeliveriesResponse(courierId, orderList);
    }

    private DeliveriesResponse mapOrderListToDeliveriesResponse(long courierId, List<Order> orderList) {
        List<DeliveryOrderInfoResponse> delivery = new ArrayList<>();
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(()->new NoSuchElementException("Написать сообщение"));

        for (Order order : orderList) {
            DeliveryRestaurantInfoResponse restaurant = new DeliveryRestaurantInfoResponse();
            String restaurantAddress = order.getRestaurantId().getAddress();
            restaurant.setAddress(restaurantAddress);

            double distanceCourierToRestaurant = getDistanceCourierToRestaurant(courier, order.getRestaurantId());
            restaurant.setDistance(distanceCourierToRestaurant);

            DeliveryCustomerInfoResponse customer = new DeliveryCustomerInfoResponse();
            String customerAddress = order.getCustomerId().getAddress();
            customer.setAddress(customerAddress);

            double distanceRestaurantToCustomer = getDistanceRestaurantToCustomer(order.getRestaurantId(), order.getCustomerId());
            customer.setDistance(distanceRestaurantToCustomer);



            DeliveryOrderInfoResponse doiResponse = new DeliveryOrderInfoResponse();
            doiResponse.setOrderId(order.getUuid());
            doiResponse.setRestaurant(restaurant);
            doiResponse.setCustomer(customer);

            doiResponse.setPayment(BigDecimal.valueOf(Math.abs(restaurant.getDistance()) + Math.abs(customer.getDistance()) ).multiply(BigDecimal.valueOf(0.2)));

            delivery.add(doiResponse);
        }
        return new DeliveriesResponse(delivery);
    }

    private double getDistanceRestaurantToCustomer(Restaurant restaurant, Customer customer) {
        String coordsRestaurant = restaurant.getAddress().split("\\|")[1];
        String coordsCustomer = customer.getAddress().split("\\|")[1];
        return getDistanceByCoords (coordsRestaurant, coordsCustomer);

    }

    private double getDistanceCourierToRestaurant(Courier courier, Restaurant restaurant) {
        String coordsCourier = courier.getCoordinates();
        String coordsRestaurant = restaurant.getAddress().split("\\|")[1];
        return getDistanceByCoords (coordsCourier, coordsRestaurant);
    }


    private OrderStatus checkStatus(String status) {
        for (OrderStatus st : OrderStatus.values()) {
            if (status.equals(st.toString())) {
                return st;
            }
        }
        throw new NoSuchElementException("Написать сообщение");
    }

    private double getDistanceByCoords(String coords1, String coords2){
        String[] arrayCoords1 = coords1.split(":");
        String[] arrayCoords2 = coords2.split(":");

        double longitudeCoord1 = Double.parseDouble(arrayCoords1[0]);
        double latitudeCoord1 = Double.parseDouble(arrayCoords1[1]);

        double longitudeCoord2 = Double.parseDouble(arrayCoords2[0]);
        double latitudeCoord2 = Double.parseDouble(arrayCoords2[1]);

        //рассчет координат в радианах
        double radLongitudeCoord1 = Math.toRadians(longitudeCoord1);
        double radLatitudeCoord1 = Math.toRadians(latitudeCoord1);

        double radLongitudeCoord2 = Math.toRadians(longitudeCoord2);
        double radLatitudeCoord2 = Math.toRadians(latitudeCoord2);

        //косинусы и синусы широт и разница долгот
        double cosLat1 = Math.cos(radLatitudeCoord1);
        double sinLat1 = Math.sin(radLatitudeCoord1);

        double cosLat2 = Math.cos(radLatitudeCoord2);
        double sinLat2 = Math.sin(radLatitudeCoord2);

        double delta = radLongitudeCoord2 - radLongitudeCoord1;
        double cosDelta = Math.cos(delta);
        double sinDelta = Math.sin(delta);

        // вычисления длины большого круга
        double y = Math.sqrt(Math.pow(cosLat2 * sinDelta, 2) +
                Math.pow(cosLat1 * sinLat2 - sinLat1 * cosLat2 * cosDelta, 2));
        double x = sinLat1 * sinLat2 + cosLat1 * cosLat2 * cosDelta;

        double atan = Math.atan2(y, x);
        double distance = atan * EARTH_RADIUS;

        return distance;
    }
}
