package com.akshay.StoreMaster.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class ProductRequestDTO {

    @NotBlank(message = "Product Name is  Required")
    private String name;
    @Size(max = 255, message = "Description can’t exceed 255 characters")
    private String description;
    @Positive(message = "Price Must be positive")
    private BigDecimal price;
    @Min(value = 0, message = "Stock Quantity Must be non-negative")
    private Integer stock_quantity;
    @NotBlank(message = "Category is required")
    private String category;
}
