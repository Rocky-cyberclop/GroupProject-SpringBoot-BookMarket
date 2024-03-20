package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.models.Order;
import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.requests.CartRequest;
import com.groupproject.bookmarket.requests.OrderRequest;
import com.groupproject.bookmarket.responses.MessageResponse;
import com.groupproject.bookmarket.services.ConfigPaymenntService;
import com.groupproject.bookmarket.services.JwtService;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/user/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigPaymenntService configPaymenntService;
    @GetMapping("cart/info")
    public ResponseEntity<?> getInfoCartUser(@RequestHeader(name = "Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return orderService.getInfoCart(userOptional.get().getId());
    }

    @PostMapping("cart/add")
    public String addToCart(@RequestBody CartRequest cartRequest,@RequestHeader(name = "Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return orderService.addToCart(cartRequest,userOptional.get().getId());
    }


    @PostMapping("cart/checkout")
    public ResponseEntity<String> checkOut(@RequestBody OrderRequest orderRequest,@RequestHeader(name = "Authorization") String token){
        System.out.println(orderRequest.getUserId());
        System.out.println(orderRequest.getCartItemIds());
        System.out.println(orderRequest.getVoucherId());
        System.out.println(orderRequest.getAddress());
        System.out.println(orderRequest.getCode());
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
//        return ResponseEntity.ok("Call function check out");
        return orderService.sendReceipt(orderRequest,userOptional.get().getId());
    }

//    @GetMapping("/order/{userId}")
//    public List<Order> getOrdersByUser(@RequestHeader(name = "Authorization") String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//
//        }
//        String email = jwtService.extractUsername(token);
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()){
//            throw new RuntimeException("User not found");
//        }
//        return orderService.getOrderInfoById(userOptional.get().getId());
//    }
    @GetMapping("/get/fullName")
    public String getFullNameeUser(@RequestHeader(name = "Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return userOptional.get().getFullName();
    }
    @GetMapping("/checkout/createUrl")
    public ResponseEntity<MessageResponse> createUrlPayment(@RequestHeader(name = "Authorization") String token,Double totalPrice) throws UnsupportedEncodingException {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return configPaymenntService.createUrlPayment(userOptional.get().getUsername(),totalPrice);
    }
    @GetMapping("/checkout/checkResponse")
    public ResponseEntity<?> createUrlPayment(@RequestBody Map<String,String> requestData) {
        return configPaymenntService.handlePaymentResult(requestData);
    }

}
