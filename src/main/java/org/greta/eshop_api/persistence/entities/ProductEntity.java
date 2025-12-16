package org.greta.eshop_api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.greta.eshop_api.exposition.dtos.ProductRequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double discount = 0.0;

    @Column
    private LocalDate promoStart;

    @Column
    private LocalDate promoEnd;

    @ManyToMany
    @JoinTable (
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories = new ArrayList<>();

    public void updateForm(ProductRequestDTO dto){
        this.name = dto.name();
        this.description = dto.description();
        this.imageUrl = dto.imageUrl();
        this.price = dto.price();
        this.isActive = dto.isActive();
        this.stock = dto.stock();
        this.discount = dto.discount();
    }
}
