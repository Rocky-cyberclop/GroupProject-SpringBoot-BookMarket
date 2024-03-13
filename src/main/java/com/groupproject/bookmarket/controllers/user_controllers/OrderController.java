package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.responses.CartResponse;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/user/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("cart/info/{useId}")
    public ResponseEntity<?> getInfoCartUser(@PathVariable Long useId){
        return orderService.getInfoCart(useId);
    }
}
