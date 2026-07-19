package com.turkcell.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "name boş olamaz")
        @Size(max = 255, message = "name en fazla 255 karakter olabilir")
        String name,

        String description,

        @NotNull(message = "price zorunludur")
        @PositiveOrZero(message = "price negatif olamaz")
        BigDecimal price,

        @NotNull(message = "stock zorunludur")
        @PositiveOrZero(message = "stock negatif olamaz")
        Integer stock
) {
}