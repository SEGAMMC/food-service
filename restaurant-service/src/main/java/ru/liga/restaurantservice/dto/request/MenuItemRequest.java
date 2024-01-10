package ru.liga.restaurantservice.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.MenuItemStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemRequest {
    private long restaurantId;

    private String name;

    private BigDecimal price;

    private String imageUrl;

    private String description;

    private MenuItemStatus status;

}
