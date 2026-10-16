package com.akshay.StoreMaster.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponseDTO {
    public OrderItemResponseDTO(Long id, int quantity, BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    private Long id;
    private int quantity;
    private BigDecimal price;
}
