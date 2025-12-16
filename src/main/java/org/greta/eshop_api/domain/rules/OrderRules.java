package org.greta.eshop_api.domain.rules;

import org.greta.eshop_api.exceptions.BadRequestExceptions;
import org.greta.eshop_api.exposition.dtos.OrderItemRequestDTO;
import org.greta.eshop_api.persistence.entities.CustomerEntity;
import org.greta.eshop_api.persistence.entities.OrderEntity;
import org.greta.eshop_api.persistence.entities.ProductEntity;

import java.util.List;

public class OrderRules {
    public static void validateProducts(List<ProductEntity> products){
        if(products.isEmpty())
            throw new RuntimeException("Aucun produit actif pour cette commande");
        boolean allInactive = products.stream().noneMatch(ProductEntity::getIsActive);

        if(allInactive)
            throw new RuntimeException("Aucun produit actif dans la commande");
    }

    public static void validateStock(OrderItemRequestDTO item, ProductEntity product) {
        if (item.quantity() > product.getStock()) {
            throw new RuntimeException("Stock insuffisant pour le produit " + product.getName());
        }
    }

    public static void validateTotal(double total){
        double max = 5000.0;
        if (total > max){
            throw new RuntimeException("Le montant total de la commande dépasse le plafond autorisé (" + max + "€)");
        }
    }

    public static void validateOrderStatus(String status) {
        List<String> allowed = List.of("PENDING", "SHIPPED", "DELIVERED", "CANCELLED");
        if (!allowed.contains(status)) {
            throw new RuntimeException("Statut de commande invalide : " + status);
        }
    }
}
