package org.greta.eshop_api.exposition.dtos;

public record CustomerResponseDTO(
        Long id,
        String first_name,
        String last_name
) {}
