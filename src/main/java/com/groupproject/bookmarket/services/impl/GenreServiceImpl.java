package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.repositories.BookRepository;
import com.groupproject.bookmarket.repositories.GenreRepository;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage) {
        if ( name == null || name.isEmpty()) {
            name = "%";
        } else {
            name = "%" + name + "%";
        }
        Pageable pageable = PageRequest.of(cPage - 1, size);
        Page<Genre> page = genreRepository.findByNameLikeIgnoreCase(pageable, name);
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
    public ResponseEntity<List<Genre>> fetchGenresByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(value -> new ResponseEntity<>(value.getGenres(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }

    @Override
    @Transactional
    public ResponseEntity<MyResponse> editGenreInfo(Long genreId, Genre genre) {
        MyResponse myResponse = new MyResponse();
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if (genreOptional.isPresent()) {
            genreOptional.get().setName(genre.getName());
            Genre genreSaved = genreRepository.save(genreOptional.get());

            myResponse.setMessage("Edit genre successfully!");
            myResponse.setData(genreSaved);
        } else {
            myResponse.setMessage("Edit genre info error!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<MyResponse> addNewGenre(Genre genre) {
        MyResponse myResponse = new MyResponse();
        Optional<Genre> genreOptional = genreRepository.findByName(genre.getName());
        if (genreOptional.isEmpty()) {
            genreRepository.save(genre);
            myResponse.setMessage("Add new genre successfully!");
        } else {
            myResponse.setMessage("This genre is already existed!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Genre> fetchGenreInfo(Long genreId) {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        return genreOptional.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.OK));
    }
}
