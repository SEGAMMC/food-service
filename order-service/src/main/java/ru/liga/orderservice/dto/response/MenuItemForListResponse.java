package ru.liga.orderservice.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemForListResponse {

    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;
}
