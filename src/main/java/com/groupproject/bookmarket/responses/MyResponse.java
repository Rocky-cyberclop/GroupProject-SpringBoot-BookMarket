package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MyResponse {
    private String message;
    private String rspCode;
    private String state;
    private Object data;

    public MyResponse() {
        message = "";
        rspCode = "200";
        state = "success";
    }
}
