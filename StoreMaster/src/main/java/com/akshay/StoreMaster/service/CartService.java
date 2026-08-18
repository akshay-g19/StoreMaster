package com.akshay.StoreMaster.service;

import com.akshay.StoreMaster.dto.AddCartDTO;
import com.akshay.StoreMaster.dto.CartItemDTO;
import com.akshay.StoreMaster.dto.CartResponseDTO;
import com.akshay.StoreMaster.entity.Cart;
import com.akshay.StoreMaster.entity.CartItem;
import com.akshay.StoreMaster.entity.Product;
import com.akshay.StoreMaster.entity.User;
import com.akshay.StoreMaster.exception.CartNotFoundException;
import com.akshay.StoreMaster.exception.ProductNotFoundException;
import com.akshay.StoreMaster.repository.CartItemRepository;
import com.akshay.StoreMaster.repository.CartRepository;
import com.akshay.StoreMaster.repository.ProductRepository;
import com.akshay.StoreMaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Transactional
    public void addCart(AddCartDTO addCartDTO) {
        log.info("Received addCart request for user: {}", addCartDTO.getUserId());

        Product product = productRepository.findById(addCartDTO.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with ID: " + addCartDTO.getProductId()));

        log.info("Fetched Product: id: {}, name: {}, price: {}", product.getId(), product.getName(), product.getPrice());

        User user = userRepository.findById(addCartDTO.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID " + addCartDTO.getUserId()));

        log.info("Fetched User: id:{}", user.getId());

        // Check if the user already has a cart
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            log.info("No existing cart found for userId : {}. Creating new cart.", user.getId());
            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            cart = cartRepository.save(cart);
            log.info("New cart created: cartId: {}", cart.getId());
        } else {
            log.info("Existing cart found: cartId: {}", cart.getId());
        }

        Optional<CartItem> existingItem = cart.getCartItemList().stream()
                .filter(
                        item -> item.getProduct().getId()
                                .equals(addCartDTO.getProductId())
                )
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            log.info("Product already in cart. Updating quantity. Old quantity: {}, adding: {}", item.getQuantity(), addCartDTO.getQuantity());
            item.setQuantity(item.getQuantity() + addCartDTO.getQuantity());
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            log.info("Updated CartItem: productId: {}, quantity: {}, price: {}", item.getProduct().getId(), item.getQuantity(), item.getPrice());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(addCartDTO.getQuantity());
            log.info("Quantity: {}, Prize: {}", addCartDTO.getQuantity(), product.getPrice());
            newItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(addCartDTO.getQuantity())));
            log.info("item prize: {}", newItem.getPrice());
            newItem.setCart(cart);
            cart.getCartItemList().add(newItem);
            log.info("Added new CartItem: productId:{}, quantity:{}, price:{}", product.getId(), addCartDTO.getQuantity(), newItem.getPrice());
        }
        for (CartItem item : cart.getCartItemList()) {
            log.info("CartItem - Product ID: {}, Name: {}, Quantity: {}, Price: {}", item.getProduct().getId(), item.getProduct().getName(), item.getQuantity(), item.getPrice());
        }

        BigDecimal total = cart.getCartItemList().stream().
                map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(total);
        cartRepository.save(cart);
        log.info("Total Prize: {}", cart.getTotalPrice());
        log.info("Cart updated: cartId:{}, totalPrice:{}", cart.getId(), cart.getTotalPrice());
    }

    @Transactional
    public CartResponseDTO viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(
                        "Cart not found for user with ID: " + userId
                ));

        List<CartItemDTO> cartItems = cart.getCartItemList().stream()
                .map(item -> new CartItemDTO(
                        item.getProduct().getId(), item.getProduct().getName(),
                        item.getQuantity(), item.getProduct().getPrice())
                ).collect(Collectors.toList());

        return new CartResponseDTO(cartItems, cart.getTotalPrice());
    }
    @Transactional
    public void removeFromCart(long userId, long productId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        Optional<CartItem> itemToRemove = cart.getCartItemList().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (itemToRemove.isEmpty()) {
            throw new RuntimeException("Product with ID: " + productId + " not found in cart");
        }

        cart.getCartItemList().remove(itemToRemove.get());

        BigDecimal total = cart.getCartItemList().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart (Long userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepository.delete(cart);
    }
}
