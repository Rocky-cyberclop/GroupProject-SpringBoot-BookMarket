package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.responses.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    ResponseEntity<?> getInfoCart(Long userId);

    @Transactional
    String addToCart(Long userId, Long bookId, Integer quantity);
}
