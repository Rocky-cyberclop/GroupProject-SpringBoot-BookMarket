package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.VoucherDTO;
import com.groupproject.bookmarket.models.*;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.requests.CartRequest;
import com.groupproject.bookmarket.requests.OrderRequest;
import com.groupproject.bookmarket.responses.*;
import com.groupproject.bookmarket.services.MailService;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
//    @Override
//    @Transactional
//    public String addToCart(Long userId, Long bookId, Integer quantity) {
//        try {
//            CartItem existItem = cartItemRepository.findByBookIdAndUserId(bookId, userId);
//            if (existItem != null) {
//                existItem.setQuantity(existItem.getQuantity() + quantity);
//                return "Item add to cart successfully";
//            } else {
//                CartItem cartItem = new CartItem();
//                cartItem.setQuantity(quantity);
//                Book book = bookRepository.findById(bookId).orElseThrow();
//                User user = userRepository.findById(userId).orElseThrow();
//                cartItem.setBook(book);
//                cartItem.setUser(user);
//                cartItemRepository.save(cartItem);
//                return "Item add to cart successfully";
//            }
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

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


//// add To Cart without Token

    @Override
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
        order.setAddress(user.getAddress());
        if (orderRequest.getVoucherId() != null) {
            Voucher voucher = voucherRepository.findById(orderRequest.getVoucherId()).orElseThrow();
            if (voucher.getQuantity() == 0) {
                throw new RuntimeException("Voucher is out of stock");
            }
            order.setVoucher(voucher);
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
            Book book = cartItem.getBook();
            int newQuantity = book.getQuantity() - cartItem.getQuantity();
            if (newQuantity < 0){
                throw  new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
            book.setQuantity(newQuantity);
            bookRepository.save(book);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savaOrder = orderRepository.save(order);
        Payment payment = new Payment();
        payment.setOrder(savaOrder);
        payment.setDate(orderRequest.getPaymentDay());
        payment.setTotal(orderItems.stream().mapToLong(item ->
                item.getPrice() * item.getQuantity()).sum());
        payment.setCode(orderRequest.getCode());
        paymentRepository.save(payment);

        return savaOrder;
    }

    @Override
    @Transactional
    public ResponseEntity<String> sendReceipt(OrderRequest orderRequest, Long userId) {
        Order order = checkout(orderRequest, userId);
            if (order != null) {
                mailService.sendDetailReceipt(orderRequest, userId);
                List<CartItem> cartItems = cartItemRepository.findAllById(orderRequest.getCartItemIds());
                cartItemRepository.deleteAll(cartItems);
                return ResponseEntity.status(HttpStatus.OK).body("Payment successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Info incorrect");
            }

    }
    @Override
    public ResponseEntity<?> getDiscountPercentAndIdByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code).orElse(null);
        if (voucher != null) {
            if (voucher.getQuantity() > 0) {
                return ResponseEntity.ok(new VoucherDTO(voucher.getId(), voucher.getPercent()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voucher is out of stock");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voucher not found");
        }
    }

    @Override
    public CartItem incrementQuantity(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            return cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Cart item not found with id: " + cartItemId);
        }
    }

    @Override
    // Giảm số lượng của một mục trong giỏ hàng
    public CartItem decrementQuantity(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                return cartItemRepository.save(cartItem);
            } else {
                // Nếu số lượng là 1 thì xóa mục khỏi giỏ hàng
                cartItemRepository.deleteById(cartItemId);
                return null; // Hoặc bạn có thể trả về một đối tượng khác để thể hiện rằng mục đã bị xóa
            }
        } else {
            throw new RuntimeException("Cart item not found with id: " + cartItemId);
        }
    }

    @Override
    // Xóa một mục khỏi giỏ hàng
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}



