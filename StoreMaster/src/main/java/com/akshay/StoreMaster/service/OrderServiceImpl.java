package com.akshay.StoreMaster.service;

import com.akshay.StoreMaster.Constants.OrderStatus;
import com.akshay.StoreMaster.dto.OrderItemResponseDTO;
import com.akshay.StoreMaster.dto.OrderResponseDTO;
import com.akshay.StoreMaster.entity.Cart;
import com.akshay.StoreMaster.entity.Order;
import com.akshay.StoreMaster.entity.OrderItem;
import com.akshay.StoreMaster.entity.Product;
import com.akshay.StoreMaster.exception.ProductNotFoundException;
import com.akshay.StoreMaster.repository.CartRepository;
import com.akshay.StoreMaster.repository.OrderRepository;
import com.akshay.StoreMaster.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponseDTO placeOrder(Long userId) {
        log.info("Placing order for userId={}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        log.info("Cart retrieved for userId={} with {} items", userId, cart.getCartItemList().size());

        if (cart.getCartItemList().isEmpty()){
            log.info("Cart is empty for userId={}", userId);
            throw new RuntimeException("Cart is Empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cart.getCartItemList().stream()
                .map(cartItem -> {
                    Product product = productRepository.findById(cartItem.getProduct().getId())
                            .orElseThrow(() -> new ProductNotFoundException(
                            String.format("Product Not found: ID=%d" , cartItem.getProduct().getId())));
                    log.info("Processing productId={} name={} requestedQty={} availableQty={}",
                            product.getId(), product.getName(), cartItem.getQuantity(), product.getStockQuantity());

                    if (product.getStockQuantity() < cartItem.getQuantity()){
                        log.info("Insufficient stock for productId={} name={} requested={} available={}",
                                product.getId(), product.getName(), cartItem.getQuantity(), product.getStockQuantity());
                        throw new RuntimeException("Insufficient stock for product" + product.getName());
                    }
                    product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
                    productRepository.save(product);
                    log.info("Reduced stock for productId={} newQty={}", product.getId(), product.getStockQuantity());

                    OrderItem item =  new OrderItem();
                    item.setProductId(cartItem.getProduct().getId());
                    item.setQuantity(cartItem.getQuantity());
                    item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    item.setOrder(order);
                    return item;
                }).toList();

        order.setItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Calculated order total={} for userId={}", total, userId);

        order.setTotal(total);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        log.info("Order placed successfully. orderId={} userId={}", savedOrder.getId(), userId);

        cart.getCartItemList().clear();
        cartRepository.save(cart);
        log.info("Cleared cart for userId={}", userId);
        return mapToOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        log.info("Updating order status. orderId={} newStatus={}", orderId, orderStatus);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not found"));
        order.setStatus(orderStatus);
        Order updatedOrder = orderRepository.save(order);
        log.info("Order status updated. orderId={} status={}", updatedOrder.getId(), updatedOrder.getStatus());
        return mapToOrderResponseDTO(updatedOrder);
    }

    @Override
    @Transactional
    public List<OrderResponseDTO> getMyOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        log.info("Found {} orders for userId={}", orders.size(), userId);
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        log.info("Found {} total orders", orders.size());
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .toList();
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        log.info("Mapping Order to DTO. orderId={}", order.getId());
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setItems(order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getId(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList());
        return dto;
    }
}
