package com.groupproject.bookmarket.requests;

import com.groupproject.bookmarket.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewVoucherRequest {
    private Voucher voucher;
    private boolean loyalProgram;
}
