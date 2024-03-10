package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.responses.DetailBook;
import com.groupproject.bookmarket.responses.ListBook;
import com.groupproject.bookmarket.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/user/book")
public class BookControllers {

    @Autowired
    private BookService bookService;

    @GetMapping("/list/{n}")
    public List<ListBook> getListBook(@PathVariable int n){
        return bookService.getListBook(n);
    }

    @GetMapping("detail/{bookId}")
    public DetailBook detailBook(@PathVariable Long bookId){
        return bookService.getDetailBook(bookId);
    }
}
