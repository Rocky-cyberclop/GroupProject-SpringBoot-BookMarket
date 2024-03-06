package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.AuthorService;
import com.groupproject.bookmarket.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateBookByTitle(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                       @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return authorService.searchPaginateByName(name, size, cPage);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<Author>> fetchGenreByBookId(@PathVariable("bookId") Long bookId) {
        return authorService.fetchAuthorsByBookId(bookId);
    }
}
