package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.models.Voucher;
import com.groupproject.bookmarket.requests.AddNewVoucherRequest;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.MailService;
import com.groupproject.bookmarket.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/voucher")
public class VoucherAdminController {
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private MailService mailService;

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateVoucherByCode(@RequestParam(value = "code", required = false, defaultValue = "") String code,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                       @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return voucherService.searchPaginateByCode(code, size, cPage);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> fetchVoucherInfoById(@PathVariable("voucherId") Long voucherId) {
        return voucherService.fetchVoucherInfo(voucherId);
    }

    @PostMapping
    public ResponseEntity<MyResponse> addNewVoucher(@RequestBody AddNewVoucherRequest request) {
        return voucherService.addNewVoucher(request);
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<MyResponse> editVoucher(@PathVariable("voucherId") Long voucherId, @RequestBody Voucher voucher) {
        return voucherService.editVoucher(voucherId, voucher);
    }
}
