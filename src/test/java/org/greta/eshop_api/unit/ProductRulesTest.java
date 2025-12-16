package org.greta.eshop_api.unit;

import org.greta.eshop_api.domain.rules.ProductRules;
import org.greta.eshop_api.persistence.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRulesTest {
    private ProductEntity activeProduct;
    private ProductEntity inactiveProduct;

    @BeforeEach
    void setup(){
        activeProduct = new ProductEntity();
        activeProduct.setName("Magic Potion");
        activeProduct.setPrice(100.0);
        activeProduct.setStock(10);
        activeProduct.setIsActive(true);
        activeProduct.setDiscount(10);
        activeProduct.setPromoStart(LocalDate.now().minusDays(1));
        activeProduct.setPromoEnd(LocalDate.now().plusDays(5));

        inactiveProduct = new ProductEntity();
        inactiveProduct.setName("Old Potion");
        inactiveProduct.setPrice(50.0);
        inactiveProduct.setStock(5);
        inactiveProduct.setIsActive(false);
        inactiveProduct.setDiscount(25);
        inactiveProduct.setPromoStart(LocalDate.now().minusDays(10));
        inactiveProduct.setPromoEnd(LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Should throw if discount above 100")
    void shouldThrowIfDiscountAbove100(){

        activeProduct.setDiscount(150);

        Exception ex = assertThrows(RuntimeException.class,
                () -> ProductRules.validPercentOfDiscount(activeProduct));

        assertTrue(ex.getMessage().contains("100"));
    }

    @Test
    @DisplayName("Should throw if discount negative")
    void shouldThrowIfDiscountNegative(){
        activeProduct.setDiscount(-20);

        Exception ex = assertThrows(RuntimeException.class,
                () -> ProductRules.validPercentOfDiscount(activeProduct));

        assertTrue(ex.getMessage().contains("0"));
    }

    @Test
    @DisplayName("Should throw if discounted price negative")
    void shouldThrowIfDiscountedPriceNegative(){
        activeProduct.setDiscount(150);

        Exception ex = assertThrows(RuntimeException.class,
                () -> ProductRules.validFinalPrice(activeProduct));

        assertTrue(ex.getMessage().contains("prix final"));
    }

    @Test
    @DisplayName("Should throw if inactive product has discount")
    void shouldThrowIfInactiveProductHasDiscount(){
        Exception ex = assertThrows(RuntimeException.class,
                () -> ProductRules.validDiscountCondition(inactiveProduct));

        assertTrue(ex.getMessage().contains("produit inactif"));
    }

    @Test
    @DisplayName("Should throw if promo expired")
    void shouldThrowIfPromoExpired(){
        Exception ex = assertThrows(RuntimeException.class,
                () -> ProductRules.validDate(inactiveProduct));

        assertTrue(ex.getMessage().contains("expirée"));
    }
}
