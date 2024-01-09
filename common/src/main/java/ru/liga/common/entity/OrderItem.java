package ru.liga.common.entity;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@Builder
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq",
            allocationSize = 1)
    private long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order orderId;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_menu_item_id")
    private RestaurantMenuItem restaurantMenuItem;

    @Column(name = "price", columnDefinition = "decimal(10, 2)")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

}
