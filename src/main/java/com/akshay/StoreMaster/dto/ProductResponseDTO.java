package com.akshay.StoreMaster.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class ProductResponseDTO {
    private Long product_ID;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock_quantity;

    private String category;
}
