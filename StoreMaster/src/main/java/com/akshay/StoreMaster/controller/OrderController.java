package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.Constants.OrderStatus;
import com.akshay.StoreMaster.dto.OrderItemResponseDTO;
import com.akshay.StoreMaster.dto.OrderResponseDTO;
import com.akshay.StoreMaster.entity.Order;
import com.akshay.StoreMaster.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storeMaster/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestParam Long userId){
        OrderResponseDTO dto = orderService.placeOrder(userId);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus){
        OrderResponseDTO dto = orderService.updateOrderStatus(orderId,orderStatus);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/myOrders")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(@RequestParam Long userId){
       List<OrderResponseDTO> myOrders= orderService.getMyOrders(userId);
        return ResponseEntity.ok(myOrders);
    }

    @GetMapping("all-orders")
    public ResponseEntity<List<OrderResponseDTO>> allOrders(){
        List<OrderResponseDTO> myOrders= orderService.getAllOrders();
        return ResponseEntity.ok(myOrders);
    }
}
