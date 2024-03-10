package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.repositories.AuthorRepository;
import com.groupproject.bookmarket.repositories.BookRepository;
import com.groupproject.bookmarket.responses.MyResponse;
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
        Page<Author> page = authorRepository.findByNameLikeIgnoreCase(pageable, name);
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

    @Override
    public ResponseEntity<MyResponse> editAuthorInfo(Long authorId, Author author) {
        MyResponse myResponse = new MyResponse();
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if (authorOptional.isPresent()) {
            authorOptional.get().setAlias(author.getAlias());
            authorOptional.get().setName(author.getName());
            Author authorSaved =  authorRepository.save(authorOptional.get());
            myResponse.setMessage("Edit author info successfully!!");
            myResponse.setData(authorSaved);
        } else {
            myResponse.setMessage("Edit author info error!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyResponse> addNewAuthor(Author author) {
        MyResponse myResponse = new MyResponse();
        Optional<Author> authorOptional = authorRepository.findByName(author.getName());
        if (authorOptional.isEmpty()) {
            authorRepository.save(author);
            myResponse.setMessage("Add new author successfully!");
        } else {
            myResponse.setMessage("Author is already existed!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Author> fetchAuthorInfo(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        return authorOptional.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }
}
