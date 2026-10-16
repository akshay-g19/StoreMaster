package com.akshay.StoreMaster.repository;

import com.akshay.StoreMaster.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
