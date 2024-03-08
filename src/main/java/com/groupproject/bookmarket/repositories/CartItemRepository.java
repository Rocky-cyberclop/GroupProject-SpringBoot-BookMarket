package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByBookIdAndUserId(Long bookId, Long userId);

    List<CartItem> findCartItemByUserId(Long userId);
}
