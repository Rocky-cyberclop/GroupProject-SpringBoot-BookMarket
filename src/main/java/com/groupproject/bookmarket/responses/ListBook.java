package com.groupproject.bookmarket.responses;

import com.groupproject.bookmarket.models.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListBook {
    private  Long bookId;
    private String bookName;
    private Long bookPrice;
    private List<String> bookImage;
}
