package com.groupproject.bookmarket.controllers.admin_controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Image;
import com.groupproject.bookmarket.requests.DeleteBooksRequest;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.BookService;
import com.groupproject.bookmarket.services.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/book")
public class BookAdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private FilesStorageService filesStorageService;


    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateBookByTitle(@RequestParam(value = "title", required = false, defaultValue = "") String title, @RequestParam(value = "size", required = false, defaultValue = "6") int size, @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return bookService.searchPaginateByTitle(title, size, cPage);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> fetchBookInfoById(@PathVariable(value = "bookId", required = true) Long bookId) {
        return bookService.fetchBookInfo(bookId);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyResponse> addNewBook(@RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                 @RequestPart("addBookRequest") String addBookRequest) throws JsonProcessingException {
        return bookService.addNewBook(images, addBookRequest);
    }
    @PutMapping(value = "/{bookId}",consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyResponse> editBook(@PathVariable(value = "bookId", required = true) Long bookId,@RequestPart(value = "images", required = false) List<MultipartFile> images,
                                                 @RequestPart("addBookRequest") String addBookRequest) throws JsonProcessingException {
        return bookService.editBook(bookId, images, addBookRequest);
    }

    @GetMapping("/images/{bookId}")
    public ResponseEntity<List<Image>> getAllImagesByBookId(@PathVariable(value = "bookId", required = true) Long bookId) {
        return bookService.fetchAllImagesByBookId(bookId);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyResponse> deleteBooks(@RequestBody DeleteBooksRequest request) {
        return bookService.deleteBooks(request.getBookIds());
    }

    @PostMapping(value = "/testImages",consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewBook(@RequestPart(value = "image", required = false) MultipartFile image) throws JsonProcessingException {
        filesStorageService.saveBookImages(List.of(image));
        return new ResponseEntity<>("Add file successfully!", HttpStatus.OK);
    }
    @GetMapping("/images-get/{imageName}")
    public @ResponseBody ResponseEntity<Resource> downloadImageFromFileSystem(@PathVariable("imageName") String imageName) throws IOException {
        System.out.println(imageName);
        Path imagePath = Paths.get("src/main/resources/static/uploads/images/book");
        Resource image = filesStorageService.loadFileWithPath(imagePath, imageName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }




}
