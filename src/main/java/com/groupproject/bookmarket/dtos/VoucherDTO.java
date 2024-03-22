package com.groupproject.bookmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private Long id;
    private Float percent;
}

