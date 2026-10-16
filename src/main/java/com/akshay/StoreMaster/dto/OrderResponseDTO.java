package com.akshay.StoreMaster.dto;

import com.akshay.StoreMaster.Constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private Long orderId;
    private Long userId;
    private List<OrderItemResponseDTO> items;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
