package org.greta.eshop_api.exposition.dtos;

public record OrderItemResponseDTO(
        Long id,
        int quantity,
        double unit_price
) {}
