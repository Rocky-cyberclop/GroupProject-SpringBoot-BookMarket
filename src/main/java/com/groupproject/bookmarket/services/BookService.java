package com.groupproject.bookmarket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Image;
import com.groupproject.bookmarket.responses.DetailBook;
import com.groupproject.bookmarket.responses.ListBook;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public ResponseEntity<PaginationResponse> searchPaginateByTitle(String title, int size, int cPage);

    ResponseEntity<MyResponse> addNewBook(List<MultipartFile> images, String addBookRequest) throws JsonProcessingException;

    ResponseEntity<Book> fetchBookInfo(Long bookId);

    ResponseEntity<MyResponse> editBook(Long bookId, List<MultipartFile> images, String addBookRequest) throws JsonProcessingException;

    ResponseEntity<List<Image>> fetchAllImagesByBookId(Long bookId);

    ResponseEntity<MyResponse> deleteBooks(List<Long> bookIds);

    List<ListBook> getListBook(int limit);

    DetailBook getDetailBook(Long bookId);
}
