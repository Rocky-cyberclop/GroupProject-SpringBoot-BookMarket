package com.groupproject.bookmarket.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseData {
    private int status = 200;
    private boolean isSuccess = true;
    private String description;
    private Object data;

}
