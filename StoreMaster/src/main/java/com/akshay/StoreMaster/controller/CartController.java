package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.dto.AddCartDTO;
import com.akshay.StoreMaster.dto.CartResponseDTO;
import com.akshay.StoreMaster.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storeMaster/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addCart(@RequestBody AddCartDTO addCartDTO) {
        cartService.addCart(addCartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart successfully");
    }

    @GetMapping("/view")
    public ResponseEntity<CartResponseDTO> viewCart(@RequestParam Long userId) {
        CartResponseDTO cart = cartService.viewCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable Long userId, @PathVariable Long productId){
        cartService.removeFromCart(userId,productId);
        return new ResponseEntity<>("Product removed from cart successfully", HttpStatus.OK);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart items removed");
    }
}
