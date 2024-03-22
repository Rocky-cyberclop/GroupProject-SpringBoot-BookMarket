package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.models.Voucher;
import com.groupproject.bookmarket.requests.AddNewVoucherRequest;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

public interface VoucherService {
    ResponseEntity<PaginationResponse> searchPaginateByCode(String code, int size, int cPage);

    ResponseEntity<MyResponse> addNewVoucher(AddNewVoucherRequest request);

    ResponseEntity<MyResponse> editVoucher(Long voucherId, Voucher voucher);

    ResponseEntity<Voucher> fetchVoucherInfo(Long voucherId);
}
