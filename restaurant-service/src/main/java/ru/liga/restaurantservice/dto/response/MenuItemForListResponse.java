package ru.liga.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.entity.Restaurant;
import ru.liga.common.enums.MenuItemStatus;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemForListResponse {

    private long id;

    private String name;
	
    private String description;

    private BigDecimal price;

    private String image_url;
}
