package org.greta.eshop_api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemEntity extends BaseEntity {

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double unit_price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
