package com.akshay.StoreMaster.service;

import com.akshay.StoreMaster.dto.AddCartDTO;
import com.akshay.StoreMaster.entity.Cart;
import com.akshay.StoreMaster.entity.CartItem;
import com.akshay.StoreMaster.entity.Product;
import com.akshay.StoreMaster.entity.User;
import com.akshay.StoreMaster.repository.CartItemRepository;
import com.akshay.StoreMaster.repository.CartRepository;
import com.akshay.StoreMaster.repository.ProductRepository;
import com.akshay.StoreMaster.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCart_NewCart_AddsProductToCart() {
        // Arrange
        AddCartDTO addCartDTO = new AddCartDTO();
        addCartDTO.setUserId(1L);
        addCartDTO.setProductId(100L);
        addCartDTO.setQuantity(2);

        Product product = new Product();
        product.setId(100L);
        product.setPrice(BigDecimal.valueOf(50));

        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setId(10L);
        cart.setUser(user);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Act
        cartService.addCart(addCartDTO);

        // Assert
        verify(cartRepository, times(2)).save(any(Cart.class));
        verify(cartItemRepository, never()).save(any(CartItem.class)); // new item is not saved directly
    }
}
