package ru.liga.notificationservice.service;

import ru.liga.common.entity.ModelMessageOrder;


public interface PushClientService {

    void distributeMessages(ModelMessageOrder model);

    boolean sendPushToCustomer(ModelMessageOrder model);

    boolean sendPushToRestaurant(ModelMessageOrder model);

    boolean sendPushToCourier(ModelMessageOrder model);

}
