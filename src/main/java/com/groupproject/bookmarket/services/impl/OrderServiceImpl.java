package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.*;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.requests.CartRequest;
import com.groupproject.bookmarket.requests.OrderRequest;
import com.groupproject.bookmarket.responses.CartResponse;
import com.groupproject.bookmarket.responses.ErrorResponse;
import com.groupproject.bookmarket.responses.ListBook;
import com.groupproject.bookmarket.services.MailService;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private VoucherRepository voucherRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MailService mailService;


    //// get To Cart without Token
    @Override
    public ResponseEntity<?> getInfoCart(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
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

            Book book = cartItem.getBook(); // Giả sử bạn đã lấy được Book từ CartItem
            if (book == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found for cart item id: " + cartItem.getId());
            }

            ListBook bookItem = new ListBook();
            bookItem.setBookId(book.getId());
            bookItem.setBookName(book.getTitle());
            bookItem.setBookPrice(book.getPrice());

            List<Image> images = imageRepository.findByBookId(book.getId());
            // Giả sử Image có phương thức getUrl() để lấy URL của hình ảnh
            bookItem.setBookImage(images.stream().map(Image::getUrl).collect(Collectors.toList()));

            cartResponse.setBook(bookItem);
            return cartResponse;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }


    //// add To Cart without Token
    @Override
    @Transactional
    public String addToCart(CartRequest cartRequest, Long userId) {
        Long bookId = cartRequest.getBookId();
        Integer quantity = cartRequest.getQuantity();
        try {
            CartItem existItem = cartItemRepository.findByBookIdAndUserId(bookId, userId);
            if (existItem != null) {
                existItem.setQuantity(existItem.getQuantity() + quantity);
                return "Item add to cart successfully";
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(quantity);
                Book book = bookRepository.findById(bookId).orElseThrow();
                User user = userRepository.findById(userId).orElseThrow();
                cartItem.setBook(book);
                cartItem.setUser(user);
                cartItemRepository.save(cartItem);
                return "Item add to cart successfully";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @Transactional
    public Order checkout(OrderRequest orderRequest, Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllById(orderRequest.getCartItemIds());
        if (cartItems.isEmpty()) {
            return null;
        }
        User user = userRepository.findById(userId).orElseThrow();
        Order order = new Order();
        order.setStatus("pending");
        order.setUser(user);
        order.setAddress(orderRequest.getAddress());
        if (orderRequest.getVoucherId() != null) {
            Voucher voucher = voucherRepository.findById(orderRequest.getVoucherId()).orElseThrow();
            if (voucher.getQuantity() == 0) {
                throw new RuntimeException("Voucher is out of stock");
            }
            order.setVoucher(voucher);
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            //                cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
//            return orderItem;
            //        }).toList();
            Book book = cartItem.getBook();
            int newQuantity = book.getQuantity() - cartItem.getQuantity();
            if (newQuantity < 0) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
            book.setQuantity(newQuantity);
            bookRepository.save(book);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savaOrder = orderRepository.save(order);
        Payment payment = new Payment();
        payment.setOrder(savaOrder);
        payment.setDate(LocalDate.now());
        payment.setTotal(orderItems.stream().mapToLong(item ->
                item.getPrice() * item.getQuantity()).sum());
        payment.setCode(orderRequest.getCode());
        paymentRepository.save(payment);
        cartItemRepository.deleteAll(cartItems);
        return savaOrder;
    }

    @Override
    @Transactional
    public ResponseEntity<String> sendReceipt(OrderRequest orderRequest, Long userId) {
        Order order = checkout(orderRequest, userId);
        User user = userRepository.findById(orderRequest.getUserId()).orElse(null);
        if (user != null) {
            if (order != null) {
                mailService.sendDetailReceipt(orderRequest, userId);
                return ResponseEntity.status(HttpStatus.OK).body("Payment successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Info incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
