package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.models.Voucher;
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
    public ResponseEntity<MyResponse> addNewVoucher(@RequestBody Voucher voucher) {
        return voucherService.addNewVoucher(voucher);
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<MyResponse> editVoucher(@PathVariable("voucherId") Long voucherId, @RequestBody Voucher voucher) {
        return voucherService.editVoucher(voucherId, voucher);
    }

    @PostMapping("/send-html-email")
    public ResponseEntity<String> sendHtmlEmail() {
        Context context = new Context();
        context.setVariable("username", "trithuc");
        context.setVariable("code", "FSDF0890F");
        context.setVariable("discount", "100.000");

        Context context2 = new Context();
        context2.setVariable("username", "hoanglong");
        context2.setVariable("code", "FU3KY0U");
        context2.setVariable("discount", "200.000");

        Context context3 = new Context();
        context3.setVariable("username", "haotran");
        context3.setVariable("code", "F89KY0II");
        context3.setVariable("discount", "140.000");

        Context context4 = new Context();
        context4.setVariable("username", "pitithuong");
        context4.setVariable("code", "FWF2423F");
        context4.setVariable("discount", "170.000");

        Context context5 = new Context();
        context5.setVariable("username", "khoa");
        context5.setVariable("code", "FSDF534F");
        context5.setVariable("discount", "210.000");

        mailService.sendEmailWithHtmlTemplate("thucb2005736@student.ctu.edu.vn", "Loyalty program trithuc", "mail", context);
        mailService.sendEmailWithHtmlTemplate("longb2014756@student.ctu.edu.vn", "Loyalty program hoanglong", "mail", context2);
        mailService.sendEmailWithHtmlTemplate("haob2014653@student.ctu.edu.vn", "Loyalty program haotran", "mail", context3);
        mailService.sendEmailWithHtmlTemplate("thuongb2014795@student.ctu.edu.vn", "Loyalty program pitithuong", "mail", context4);
        mailService.sendEmailWithHtmlTemplate("khoab2005719@student.ctu.edu.vn", "Loyalty program khoa", "mail", context5);


        return new ResponseEntity<>("Send mail successfully!", HttpStatus.OK);
    }
}
