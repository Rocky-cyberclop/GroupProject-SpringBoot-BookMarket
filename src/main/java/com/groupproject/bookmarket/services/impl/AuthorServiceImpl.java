package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.repositories.AuthorRepository;
import com.groupproject.bookmarket.repositories.BookRepository;
import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public ResponseEntity<List<Author>> fetchAllAuthor() {
        return new ResponseEntity<>(authorRepository.findAll().subList(0, 100), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage) {
        if ( name == null || name.isEmpty()) {
            name = "%";
        } else {
            name = "%" + name + "%";
        }
        Pageable pageable = PageRequest.of(cPage - 1, size);
        Page<Author> page = authorRepository.findByNameLike(pageable, name);
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
    public ResponseEntity<List<Author>> fetchAuthorsByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(value -> new ResponseEntity<>(value.getAuthors(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }
}
