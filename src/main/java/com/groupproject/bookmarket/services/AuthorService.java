package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {
    public ResponseEntity<List<Author>> fetchAllAuthor();

    ResponseEntity<PaginationResponse> searchPaginateByName(String name, int size, int cPage);
}
