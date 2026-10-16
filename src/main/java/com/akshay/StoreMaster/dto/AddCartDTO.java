package com.akshay.StoreMaster.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddCartDTO {
    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Product ID must not be null")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
