package com.akshay.StoreMaster.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;

    public CartResponseDTO(List<CartItemDTO> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

}
