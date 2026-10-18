package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.Constants.OrderStatus;
import com.akshay.StoreMaster.dto.OrderResponseDTO;
import com.akshay.StoreMaster.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestParam Long userId) {
        OrderResponseDTO dto = orderService.placeOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long orderId,
            @RequestParam OrderStatus orderStatus) {
        OrderResponseDTO dto = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/myOrders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(@RequestParam Long userId) {
        List<OrderResponseDTO> myOrders = orderService.getMyOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(myOrders);
    }

    @GetMapping("all-orders")
    public ResponseEntity<List<OrderResponseDTO>> allOrders() {
        List<OrderResponseDTO> myOrders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(myOrders);
    }
}
