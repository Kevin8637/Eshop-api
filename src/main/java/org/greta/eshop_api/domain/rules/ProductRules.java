package org.greta.eshop_api.domain.rules;

import org.greta.eshop_api.persistence.entities.ProductEntity;

import java.time.LocalDate;

public class ProductRules {
    public static void validateBeforeCreation(ProductEntity product){
        if (product.getPrice() <= 0){
            throw new RuntimeException("Le prix doit être supérieur à 0.");
        }
        if (product.getStock() <= 0){
            throw new RuntimeException("Le stock ne peut pas être négatif");
        }
    }

    public static void validateBeforeUpdate(ProductEntity product){
        if(product.getPrice() > 10000){
            throw new RuntimeException("Le prix dépasse la limite autorisée.");
        }
    }

    public static void validPercentOfDiscount(ProductEntity product){
        if(product.getDiscount() > 100){
            throw new RuntimeException("La réduction ne peut pas être supérieur à 100%");
        }

        if(product.getDiscount() < 0){
            throw new RuntimeException("La réduction ne peut pas être inférieure à 0%");
        }
    }

    public static void validFinalPrice(ProductEntity product){
        if(product.getPrice() - product.getDiscount() < 0){
            throw new RuntimeException("Le prix final du produit ne peut pas être inférieur à 0€");
        }
    }

    public static void validDiscountCondition(ProductEntity product){
        if (!product.getIsActive() && product.getDiscount() > 0) {
            throw new RuntimeException("Un produit inactif ne peut pas avoir de remise");
        }
    }

    public static void validDate(ProductEntity product){
        if (product.getPromoEnd() == null) {
            return;
        }

        if(product.getPromoStart().isAfter(product.getPromoEnd())) {
            throw new RuntimeException("La date de début de la promotion ne peut pas être après la date de fin de la promotion");
        }

        LocalDate today = LocalDate.now();

        if (today.isAfter(product.getPromoEnd())) {
            throw new RuntimeException("La promotion est expirée");
        }
    }
}
