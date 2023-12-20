package ru.liga.restaurantservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.MenuItemStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemStatusRequest {
    private MenuItemStatus status;
}
