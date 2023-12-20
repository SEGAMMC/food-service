package ru.liga.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq",  allocationSize = 1)
    private long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order orderId;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "restraunt_menu_item")
    private RestaurantMenuItem restrauntMenuItem;

    @Column(name = "price", columnDefinition = "decimal(10, 2)")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

}