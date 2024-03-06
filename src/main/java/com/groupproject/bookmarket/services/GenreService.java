package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage);

    ResponseEntity<List<Genre>> fetchGenresByBookId(Long bookId);
}
