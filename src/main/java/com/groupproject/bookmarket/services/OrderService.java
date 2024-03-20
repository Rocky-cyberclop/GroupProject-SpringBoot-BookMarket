package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Order;
import com.groupproject.bookmarket.requests.CartRequest;
import com.groupproject.bookmarket.requests.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.groupproject.bookmarket.responses.CartResponse;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;

import java.util.Map;

public interface OrderService {

    ResponseEntity<?> getInfoCart(Long userId);

    //// add To Cart without Token
    @Transactional
    String addToCart(Long userId, Long bookId, Integer quantity);

    ResponseEntity<PaginationResponse> searchPaginateByQ(String q, int size, int cPage);

    ResponseEntity<MyResponse> updateOrderStatus(Long orderId, Map<String, String> status);

    ResponseEntity<Order> getOrderInfoById(Long orderId);
}
