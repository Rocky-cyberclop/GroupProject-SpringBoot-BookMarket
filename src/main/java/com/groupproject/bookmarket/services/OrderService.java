package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.dtos.OrderHistoryDto;
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

    ResponseEntity<PaginationResponse> searchPaginateByQ(String q, int size, int cPage);

    ResponseEntity<MyResponse> updateOrderStatus(Long orderId, Map<String, String> status);

    ResponseEntity<Order> getOrderInfoById(Long orderId);

    @Transactional
    String addToCart(CartRequest cartRequest, Long userId);

    ResponseEntity<String> sendReceipt(OrderRequest orderRequest, Long userId);

    List<OrderHistoryDto> getOrdersByUser(String token);
}
