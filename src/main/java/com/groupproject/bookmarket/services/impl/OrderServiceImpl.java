package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.CartItem;
import com.groupproject.bookmarket.models.Image;
import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.BookRepository;
import com.groupproject.bookmarket.repositories.CartItemRepository;
import com.groupproject.bookmarket.repositories.ImageRepository;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.responses.CartResponse;
import com.groupproject.bookmarket.responses.ErrorResponse;
import com.groupproject.bookmarket.responses.ListBook;
import com.groupproject.bookmarket.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try{
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
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
