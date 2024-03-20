package com.groupproject.bookmarket.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class OrderRequest {
    private  Long userId;
    private List<Long> cartItemIds;
    private String address;
    private String code;
    private Long voucherId;


}
