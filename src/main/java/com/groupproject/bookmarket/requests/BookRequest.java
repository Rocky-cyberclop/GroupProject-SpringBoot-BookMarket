package com.groupproject.bookmarket.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    private String title;
    private String description;
    private String language;
    private String publishDate;
    private LocalDate lastUpdate;
    private Long price;
    private Integer quantity;
    private Boolean isDelete;
    private List<Long> authorIds;
    private List<Long> genreIds;
}
