package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.*;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.responses.*;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OrderRepository orderRepository;


    //// get To Cart without Token
    @Override
    public ResponseEntity<?> getInfoCart(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_FOUND, "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }
        List<CartItem> cartItems = cartItemRepository.findCartItemByUserId(userId);
        if (cartItems.isEmpty()) {
            ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_FOUND, "Cart items not found for this user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }
        List<CartResponse> cartResponses = cartItems.stream().map(cartItem -> {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartId(cartItem.getId());
            cartResponse.setCartQuantity(cartItem.getQuantity());
            Book book = bookRepository.findById(cartItem.getId()).get();
            ListBook bookItem = new ListBook();
            bookItem.setBookId(book.getId());
            bookItem.setBookName(book.getTitle());
            bookItem.setBookPrice(book.getPrice());
            List<Image> images = imageRepository.findByBookId(book.getId());
            bookItem.setBookImage(images);
            cartResponse.setBook(bookItem);
            return cartResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }


    //// add To Cart without Token
    @Override
    @Transactional
    public String addToCart(Long userId, Long bookId, Integer quantity) {
        try {
            CartItem existItem = cartItemRepository.findByBookIdAndUserId(bookId, userId);
            if (existItem != null) {
                existItem.setQuantity(existItem.getQuantity() + quantity);
                return "200";
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(quantity);
                Book book = bookRepository.findById(bookId).orElseThrow();
                User user = userRepository.findById(userId).orElseThrow();
                cartItem.setBook(book);
                cartItem.setUser(user);
                cartItemRepository.save(cartItem);
                return "200";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public ResponseEntity<PaginationResponse> searchPaginateByQ(String q, int size, int cPage) {
        if (q == null || q.isEmpty()) {
            q = "%";
        } else {
            q = "%" + q + "%";
        }
        Pageable pageable = PageRequest.of(cPage - 1, size);
        Page<Order> page = orderRepository.findByAddressLikeOrStatusLike(pageable, q, q);
        Pagination pagination = Pagination.builder()
                .currentPage(cPage)
                .size(size)
                .totalPage(page.getTotalPages())
                .totalResult((int) page.getTotalElements())
                .build();
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .data(page.getContent())
                .pagination(pagination)
                .build();
        return new ResponseEntity<>(paginationResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyResponse> updateOrderStatus(Long orderId, Map<String, String> request) {
        MyResponse myResponse = new MyResponse();
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            myResponse.setMessage("This order is not exist!");
            myResponse.setRspCode("400");
            myResponse.setState("error");
        } else {
            orderOptional.get().setStatus(request.get("status"));
            Order updatedOrder = orderRepository.save(orderOptional.get());
            myResponse.setMessage("Update status success!");
            myResponse.setData(updatedOrder);
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> getOrderInfoById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        return orderOptional.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElse(null);
    }
}
