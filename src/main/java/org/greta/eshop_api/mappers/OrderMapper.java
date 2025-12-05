package org.greta.eshop_api.mappers;

import org.greta.eshop_api.exposition.dtos.OrderRequestDTO;
import org.greta.eshop_api.exposition.dtos.OrderResponseDTO;
import org.greta.eshop_api.persistence.entities.OrderEntity;

public class OrderMapper {
    public static OrderEntity toEntity(OrderRequestDTO dto){
        OrderEntity entity = new OrderEntity();
        entity.setStatus(dto.status());
        return entity;
    }

    public static OrderResponseDTO toDto(OrderEntity entity){
        return new OrderResponseDTO(
                entity.getId(),
                entity.getStatus()
        );
    }
}
