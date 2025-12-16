package org.greta.eshop_api.exposition.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDTO(
   @NotNull
   @Positive
   int quantity,
   @NotNull
   @Positive
   double unit_price
) {}
