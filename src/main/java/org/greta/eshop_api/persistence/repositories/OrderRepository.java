package org.greta.eshop_api.persistence.repositories;

import org.greta.eshop_api.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
