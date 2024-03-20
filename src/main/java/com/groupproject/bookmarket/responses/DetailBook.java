package com.groupproject.bookmarket.responses;

import com.groupproject.bookmarket.models.Author;
import com.groupproject.bookmarket.models.Comment;
import com.groupproject.bookmarket.models.Genre;
import com.groupproject.bookmarket.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailBook {

    private  Long bookId;
    private String bookTitle;
    private String bookDescription;
    private Long bookPrice;
    private Integer bookQuantity;
    private List<Author> bookAuthorList;
    private List<Genre> bookGenres;
    private List<Image> bookImage;
    private  List<Comment> bookComment;

}
