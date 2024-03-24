package com.groupproject.bookmarket.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderHistoryDto {
    private Long id;
    private String status;
    private String address;
    private PaymentDto payment;
    private List<OrderItemDto> orderItems;
}
