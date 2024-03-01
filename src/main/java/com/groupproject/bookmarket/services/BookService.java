package com.groupproject.bookmarket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public ResponseEntity<PaginationResponse> searchPaginateByTitle(String title, int size, int cPage);

    ResponseEntity<MyResponse> addNewBook(List<MultipartFile> images, String addBookRequest) throws JsonProcessingException;
}
