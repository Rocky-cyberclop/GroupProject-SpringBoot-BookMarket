package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoResponse {
    private String orderInfo;
    private String bankCode;
    private String bankTranNo;
    private LocalDate payDay;
    private String message;
    private String resCode;
}