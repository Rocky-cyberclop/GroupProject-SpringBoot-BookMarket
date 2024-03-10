package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage);

    ResponseEntity<List<Genre>> fetchGenresByBookId(Long bookId);

    ResponseEntity<MyResponse> editGenreInfo(Long genreId, Genre genre);

    ResponseEntity<MyResponse> addNewGenre(Genre genre);

    ResponseEntity<Genre> fetchGenreInfo(Long genreId);
}
