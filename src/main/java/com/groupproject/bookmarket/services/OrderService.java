package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Order;
import com.groupproject.bookmarket.responses.CartResponse;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface OrderService {

    ResponseEntity<?> getInfoCart(Long userId);

    @Transactional
    String addToCart(Long userId, Long bookId, Integer quantity);

    ResponseEntity<PaginationResponse> searchPaginateByQ(String q, int size, int cPage);

    ResponseEntity<MyResponse> updateOrderStatus(Long orderId, Map<String, String> status);

    ResponseEntity<Order> getOrderInfoById(Long orderId);
}
