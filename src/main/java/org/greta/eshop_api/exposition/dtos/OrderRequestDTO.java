package org.greta.eshop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequestDTO(
        @NotBlank
        @Size(max = 50, message = "Le status ne peut pas dépasser 50 caractères")
        String status
) {}
