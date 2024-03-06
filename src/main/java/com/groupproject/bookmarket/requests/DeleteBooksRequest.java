package com.groupproject.bookmarket.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBooksRequest {
    private List<Long> bookIds;
}
