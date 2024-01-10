package ru.liga.common.entity;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.common.enums.MenuItemStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "restaurant_menu_items")
public class RestaurantMenuItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "restaurant_menu_item_seq_gen")
    @SequenceGenerator(name = "restaurant_menu_item_seq_gen",
            sequenceName = "restaurant_menu_item_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "price", columnDefinition = "decimal(10, 2)")
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MenuItemStatus status;

}
