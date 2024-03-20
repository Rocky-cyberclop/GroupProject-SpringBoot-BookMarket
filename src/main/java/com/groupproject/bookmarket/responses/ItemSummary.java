package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSummary {
    private String itemName;
    private int quantity;
    private long price;
}
