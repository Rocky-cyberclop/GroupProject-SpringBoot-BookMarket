package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.models.Order;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/order")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateGenreByName(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                       @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return orderService.searchPaginateByQ(q, size, cPage);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderInfo(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderInfoById(orderId);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<MyResponse> fetchPaginateGenreByQuery(@PathVariable("orderId") Long orderId, @RequestBody Map<String, String> status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
