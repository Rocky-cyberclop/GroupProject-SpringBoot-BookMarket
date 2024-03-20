package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Comment;
import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.requests.CommentRequest;
import com.groupproject.bookmarket.responses.DetailBook;
import com.groupproject.bookmarket.responses.ListBook;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.BookService;
import com.groupproject.bookmarket.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/user/book")
public class BookControllers {

    @Autowired
    private BookService bookService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list/{n}")
    public List<ListBook> getListBook(@PathVariable int n) {
        return bookService.getListBook(n);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> getPaginationBook(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                                                                @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentpage) {
        return bookService.getPaginationBook(title, size, currentpage);
    }
    @GetMapping("detail/{bookId}")
    public DetailBook detailBook(@PathVariable Long bookId) {
        return bookService.getDetailBook(bookId);
    }

    @GetMapping("comment/{bookId}")
    public List<CommentRequest> ListComment(@PathVariable Long bookId) {
        return bookService.getComment(bookId);
    }

    @PostMapping("comment/add/{bookId}")
    public ResponseEntity<String> AddComment(@PathVariable Long bookId,@RequestHeader(name = "Authorization") String token,@RequestBody Comment comment){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

        }
        String email = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return bookService.addComment(bookId,userOptional.get().getId(), comment);
    }
    @PostMapping("comment/edit/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId,@RequestBody CommentRequest commentRequest){
        System.out.println(commentRequest.getRating());
        System.out.println(commentRequest.getContent());
        return bookService.EditComment(commentId,commentRequest);
    }

    @PostMapping("comment/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return bookService.deleteComment(commentId);
    }




}
