package org.greta.eshop_api.mappers;

import org.greta.eshop_api.exposition.dtos.CustomerRequestDTO;
import org.greta.eshop_api.exposition.dtos.CustomerResponseDTO;
import org.greta.eshop_api.persistence.entities.CustomerEntity;

public class CustomerMapper {
    public static CustomerEntity toEntity(CustomerRequestDTO dto){
        CustomerEntity entity = new CustomerEntity();
        entity.setFirst_name(dto.first_name());
        entity.setLast_name(dto.last_name());
        return entity;
    }

    public static CustomerResponseDTO toDto(CustomerEntity entity){
        return new CustomerResponseDTO(
                entity.getId(),
                entity.getFirst_name(),
                entity.getLast_name()
        );
    }
}
