package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage);
}
