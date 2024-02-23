package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
