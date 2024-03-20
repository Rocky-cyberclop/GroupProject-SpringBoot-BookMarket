package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Order;
import com.groupproject.bookmarket.requests.CartRequest;
import com.groupproject.bookmarket.requests.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    ResponseEntity<?> getInfoCart(Long userId);

    //// add To Cart without Token
    @Transactional
    String addToCart(CartRequest cartRequest, Long userId);

    @Transactional
    ResponseEntity<String> sendReceipt(OrderRequest orderRequest, Long userId);

    List<Order> getOrdersByUser(Long userId);

    //// add To Cart without Token

}
