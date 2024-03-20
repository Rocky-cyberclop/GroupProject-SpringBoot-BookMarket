package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoResponse {
    private String orderInfo;
    private String bankCode;
    private String bankTranNo;
    private LocalDateTime payDay;
}