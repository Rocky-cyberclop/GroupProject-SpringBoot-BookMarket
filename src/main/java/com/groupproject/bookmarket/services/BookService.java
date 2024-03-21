package com.groupproject.bookmarket.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Comment;
import com.groupproject.bookmarket.models.Image;
import com.groupproject.bookmarket.requests.CommentRequest;
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


    ResponseEntity<PaginationResponse> getPaginationBook(String title, int size, int currenPage);

    DetailBook getDetailBook(Long bookId);

    List<CommentRequest> getComment(Long bookId);

    ResponseEntity<String> addComment(Long bookId, Long userId, Comment comment);

    ResponseEntity<String> EditComment(Long commentId, CommentRequest commentRequest);

    ResponseEntity<String> deleteComment(Long commentId);
}
