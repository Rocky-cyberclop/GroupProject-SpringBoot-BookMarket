package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

public interface GenreService {
    ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage);
}
