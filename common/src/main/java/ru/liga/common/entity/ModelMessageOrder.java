package ru.liga.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.ActionType;
import ru.liga.common.enums.MessageType;
import ru.liga.common.enums.OrderStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelMessageOrder {

    private UUID uuid;

    private long customerId;

    private long restaurantId;

    private long courierId;

    private MessageType type;

    private OrderStatus orderStatus;

    private ActionType action;
}
