package com.groupproject.bookmarket.controllers.admin_controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateBookByTitle(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                       @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return bookService.searchPaginateByTitle(title, size, cPage);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyResponse> addNewBook(@RequestPart(value = "images", required = false) List<MultipartFile> images,
//                                                 @RequestPart("description") String description,
                                                 @RequestPart("addBookRequest") String addBookRequest) throws JsonProcessingException {
        return bookService.addNewBook(images, addBookRequest);
    }
}
