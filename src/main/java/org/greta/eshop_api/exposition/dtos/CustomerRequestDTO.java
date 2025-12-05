package org.greta.eshop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
        @NotBlank(message = "Le prénom ne peut pas être vide")
        @Size(max = 100, message = "Le prénom ne peut dépasser 100 caractères")
        String first_name,

        @NotBlank(message = "Le nom ne peut pas être vide")
        @Size(max = 100, message = "Le prénom ne peut dépasser 100 caractères")
        String last_name
) {}
