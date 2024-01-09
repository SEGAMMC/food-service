package ru.liga.restaurantservice.dto.response;

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
public class MenuItemResponse {

    private long id;

    private long restaurantId;

    private String name;

    private BigDecimal price;

    private String imageUrl;

    private String description;

    private MenuItemStatus status;

}
