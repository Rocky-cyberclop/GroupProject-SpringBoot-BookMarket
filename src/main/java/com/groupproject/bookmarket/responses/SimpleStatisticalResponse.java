package com.groupproject.bookmarket.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleStatisticalResponse {
    private Long totalUsers;
    private Long totalOrders;
    private Long totalBooks;
    private Long totalSales;
}
