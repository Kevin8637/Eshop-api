package org.greta.eshop_api.domain.rules;

import org.greta.eshop_api.exceptions.BadRequestExceptions;
import org.greta.eshop_api.persistence.entities.CustomerEntity;
import org.greta.eshop_api.persistence.entities.OrderEntity;
import org.greta.eshop_api.persistence.entities.ProductEntity;

import java.util.List;

public class OrderRules {
    private static final List<String> orderStatus = List.of("PENDING", "SHIPPED", "DELIVERED", "CANCELLED");

    public static void validateCustomer(CustomerEntity customer){
    }

    public static void validateProducts(ProductEntity product){
    }

    public static void validateStock(){
    }

    public static void validateTotal(){
    }

    public static void validateOrderStatus(OrderEntity order){
        if(!orderStatus.contains(order.getStatus())){
            throw new BadRequestExceptions("Le status ne correspond pas");
        }
    }
}
