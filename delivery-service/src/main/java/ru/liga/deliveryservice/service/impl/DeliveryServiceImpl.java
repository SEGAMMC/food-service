package ru.liga.deliveryservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.*;
import ru.liga.common.enums.ActionType;
import ru.liga.common.enums.CourierStatus;
import ru.liga.common.enums.MessageType;
import ru.liga.common.enums.OrderStatus;
import ru.liga.deliveryservice.dto.request.OrderStatusRequest;
import ru.liga.deliveryservice.dto.request.OrdersByStatusAndCoordsRequest;
import ru.liga.deliveryservice.dto.response.DeliveriesResponse;
import ru.liga.deliveryservice.dto.response.DeliveryCustomerInfoResponse;
import ru.liga.deliveryservice.dto.response.DeliveryOrderInfoResponse;
import ru.liga.deliveryservice.dto.response.DeliveryRestaurantInfoResponse;
import ru.liga.deliveryservice.feign_core.FeignToOrderService;
import ru.liga.deliveryservice.repository.CourierRepository;
import ru.liga.deliveryservice.service.DeliveryService;
import ru.liga.deliveryservice.service.RabbitMQProducerService;

/**
 * Сервис для работы курьеров с заказами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private static final double KOEF_DISTANCE = 0.05;
    private static final double EARTH_RADIUS = 6372795; //радиус земли в метрах
    private static final int DEFAULT_COUNT_COURIERS = 5;
    private static final double PAY_BY_KM = 0.2;

    private final FeignToOrderService feignToOrderService;

    private final CourierRepository courierRepository;

    private final RabbitMQProducerService rabbitMQProducerService;

    private final ObjectMapper objectMapper;

    /**
     * Обновление статуса заказа
     *
     * @param uuid        идентификационный номер заказа
     * @param orderStatus новый статус заказа
     */
    @Override
    public void updateOrderStatus(UUID uuid, OrderStatusRequest orderStatus) {
        feignToOrderService.updateOrderStatus(uuid, orderStatus);
    }

    /**
     * Получение списка доступных заказов для доставки с соответствующим
     * статусом заказа
     *
     * @param courierId идентификационный номер курьера
     * @param status    статус заказа
     * @return возвращает список заказов на доставку с соответствиющим статусом
     */
    @Override
    public DeliveriesResponse getOrdersDeliveryByStatus(long courierId, String status) {
        String strStatus = "DELIVERY_" + status.toUpperCase();
        OrderStatus orderStatus = checkStatus(strStatus);

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));
        String[] coordsCourier = courier.getCoordinates().split(":");

        var ordersByStatusAndCoordsRequest = OrdersByStatusAndCoordsRequest.builder()
                .coordX(coordsCourier[0])
                .coordY(coordsCourier[1])
                .orderStatus(orderStatus)
                .build();

        List<Order> orderList = feignToOrderService
                .getOrderListByStatus(ordersByStatusAndCoordsRequest);
        return mapOrderListToDeliveriesResponse(courierId, orderList);
    }

    /**
     * Запуск поиска курьеров и рассылки уведомлений курьеру
     *
     * @param modelMessageOrder информация о заказе из rabbitMQ
     */
    @Override
    public void startDeliveryOrder(ModelMessageOrder modelMessageOrder) {
        modelMessageOrder.setType(MessageType.PUSH);
        modelMessageOrder.setAction(ActionType.FIND_COURIER);

        OrderInfo orderInfo = feignToOrderService.getOrderInfoByUuid(modelMessageOrder
                .getUuid());
        List<Long> couriersIdOnDelivery = preSearchCouriers(orderInfo);
        for (Long courierId : couriersIdOnDelivery) {
            modelMessageOrder.setCourierId(courierId);
            String message = mapModelMessageToString(modelMessageOrder);
            rabbitMQProducerService.sendPushToNotificationsExchange(message);
        }
    }

    /**
     * Подтверждение от курьера, что он доставит заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @Override
    public void courierTakeOrder(long courierId, UUID uuid) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));
        feignToOrderService.addCourierInOrder(uuid, courier);
    }

    /**
     * Подтверждение от курьера, что он забрал заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @Override
    public void courierPickUPOrder(long courierId, UUID uuid) {
        //TODO проверить что курьер существует
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));
        feignToOrderService.updateOrderStatus(uuid,
                new OrderStatusRequest(OrderStatus.DELIVERY_DELIVERING));
    }

    /**
     * Подтверждение от курьера, что он доставил заказ
     *
     * @param courierId идентификационный номер курьера
     * @param uuid      идентификационный номер заказа
     */
    @Override
    public void courierCompleteOrder(long courierId, UUID uuid) {
        //TODO проверить что курьер существует
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));
        feignToOrderService.updateOrderStatus(uuid,
                new OrderStatusRequest(OrderStatus.DELIVERY_COMPLETE));
    }

    /**
     * Предварительный подбор курьеров из 5 человек
     *
     * @param orderInfo информация о заказе неоходимая для курьера
     */
    private List<Long> preSearchCouriers(OrderInfo orderInfo) {
        String[] coordsRestaurantTemp = orderInfo.getRestaurantAddress().split("\\|");
        String coordsRestaurant = coordsRestaurantTemp[1];
        String[] coordsTemp = coordsRestaurant.split(":");
        double coordsRestaurantX = Double.parseDouble(coordsTemp[0]);
        double coordsRestaurantY = Double.parseDouble(coordsTemp[1]);

        List<Courier> couriers = courierRepository
                .findCouriersByStatus(CourierStatus.ACTIVE);

        Map<Double, Long> listCouriers = new TreeMap<>();
        for (Courier courier : couriers) {
            String[] coordsCourier = courier.getCoordinates().split(":");
            double coordsCourierX = Double.parseDouble(coordsCourier[0]);
            double coordsCourierY = Double.parseDouble(coordsCourier[1]);

            if (Math.abs(coordsCourierX - coordsRestaurantX) <= KOEF_DISTANCE
                    && Math.abs(coordsCourierY - coordsRestaurantY) <= KOEF_DISTANCE) {
                double distance = getDistanceByCoords(coordsRestaurant,
                        courier.getCoordinates());
                listCouriers.put(distance, courier.getId());
            }
        }
        Iterator<Map.Entry<Double, Long>> iterator = listCouriers.entrySet().iterator();
        List<Long> couriersId = new ArrayList<>(DEFAULT_COUNT_COURIERS);
        for (int i = 0; i < DEFAULT_COUNT_COURIERS; i++) {
            if (iterator.hasNext()) {
                Map.Entry<Double, Long> courier = iterator.next();
                couriersId.add(courier.getValue());
            }
        }
        return couriersId;
    }

    private DeliveriesResponse mapOrderListToDeliveriesResponse(long courierId,
                                                                List<Order> orderList) {
        List<DeliveryOrderInfoResponse> delivery = new ArrayList<>();
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NoSuchElementException("Написать сообщение"));

        for (Order order : orderList) {
            String restaurantAddress = order.getRestaurantId().getAddress();
            double distanceCourierToRestaurant = getDistanceCourierToRestaurant(courier,
                    order.getRestaurantId());

            var restaurant = new DeliveryRestaurantInfoResponse(restaurantAddress,
                    distanceCourierToRestaurant);

            String customerAddress = order.getCustomerId().getAddress();
            double distanceRestaurantToCustomer =
                    getDistanceRestaurantToCustomer(order.getRestaurantId(),
                            order.getCustomerId());

            var customer = new DeliveryCustomerInfoResponse(customerAddress,
                    distanceRestaurantToCustomer);

            BigDecimal payment = BigDecimal.valueOf(Math.abs(restaurant.getDistance())
                    + Math.abs(customer.getDistance())).multiply(
                    BigDecimal.valueOf(PAY_BY_KM));

            DeliveryOrderInfoResponse doiResponse = DeliveryOrderInfoResponse.builder()
                    .orderId(order.getUuid())
                    .restaurant(restaurant)
                    .payment(payment)
                    .customer(customer)
                    .build();
            delivery.add(doiResponse);
        }
        return new DeliveriesResponse(delivery);
    }

    private double getDistanceRestaurantToCustomer(Restaurant restaurant,
                                                   Customer customer) {
        String coordsRestaurant = restaurant.getAddress().split("\\|")[1];
        String coordsCustomer = customer.getAddress().split("\\|")[1];
        return getDistanceByCoords(coordsRestaurant, coordsCustomer);

    }

    private double getDistanceCourierToRestaurant(Courier courier,
                                                  Restaurant restaurant) {
        String coordsCourier = courier.getCoordinates();
        String coordsRestaurant = restaurant.getAddress().split("\\|")[1];
        return getDistanceByCoords(coordsCourier, coordsRestaurant);
    }

    private OrderStatus checkStatus(String status) {
        for (OrderStatus st : OrderStatus.values()) {
            if (status.equals(st.toString())) {
                return st;
            }
        }
        throw new NoSuchElementException("Написать сообщение");
    }

    private double getDistanceByCoords(String coords1, String coords2) {
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
        double y = Math.sqrt(Math.pow(cosLat2 * sinDelta, 2)
                + Math.pow(cosLat1 * sinLat2 - sinLat1 * cosLat2 * cosDelta, 2));
        double x = sinLat1 * sinLat2 + cosLat1 * cosLat2 * cosDelta;

        double atan = Math.atan2(y, x);
        double distance = atan * EARTH_RADIUS;

        return distance;
    }

    /**
     * Маппинг сообщения типа ModelMessageOrder в String
     *
     * @param modelMessageOrder объект сообщения для push-уведомления (entity)
     * @return возвращается объект сообщения в формате String  (entity)
     */
    private String mapModelMessageToString(ModelMessageOrder modelMessageOrder) {
        String messageModelToString = null;
        try {
            messageModelToString = objectMapper.writeValueAsString(modelMessageOrder);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return messageModelToString;
    }
}
