package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummary {
    private List<ItemSummary> items;
    private long totalAmount;
    private int totalQuantity;
}
