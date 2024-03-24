package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.models.Voucher;
import com.groupproject.bookmarket.repositories.OrderRepository;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.repositories.VoucherRepository;
import com.groupproject.bookmarket.requests.AddNewVoucherRequest;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.MailService;
import com.groupproject.bookmarket.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MailService mailService;

    @Override
    public ResponseEntity<PaginationResponse> searchPaginateByCode(String code, int size, int cPage) {
        if ( code == null || code.isEmpty()) {
            code = "%";
        } else {
            code = "%" + code + "%";
        }
        Pageable pageable = PageRequest.of(cPage - 1, size);
        Page<Voucher> page = voucherRepository.findByCodeLikeIgnoreCase(pageable, code);
        Pagination pagination = Pagination.builder()
                .currentPage(cPage)
                .size(size)
                .totalPage(page.getTotalPages())
                .totalResult((int) page.getTotalElements())
                .build();
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .data(page.getContent())
                .pagination(pagination)
                .build();
        return new ResponseEntity<>(paginationResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<MyResponse> addNewVoucher(AddNewVoucherRequest request) {
        MyResponse myResponse = new MyResponse();
        Optional<Voucher> existVoucher = voucherRepository.findByCode(request.getVoucher().getCode());
        if (existVoucher.isEmpty()) {
            voucherRepository.save(request.getVoucher());
            myResponse.setMessage("Add new voucher successfully!");

            if(request.isLoyalProgram()) {
                Context context = new Context();
                context.setVariable("code", request.getVoucher().getCode());
                context.setVariable("discount", request.getVoucher().getHighestRate());

                List<String> listLoyalUsers = orderRepository.getMailsSendVoucher();
                listLoyalUsers.forEach(loyalUser -> {
                    context.setVariable("username", loyalUser);
                    mailService.sendEmailWithHtmlTemplate(loyalUser, "Loyalty program", "mail", context);
                });
                System.out.println("Send mail");
            }
        } else {
            myResponse.setMessage("This voucher code is already existed!!!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MyResponse> editVoucher(Long voucherId, Voucher voucher) {
        MyResponse myResponse = new MyResponse();
        Optional<Voucher>  voucherOptional = voucherRepository.findById(voucherId);
        if (voucherOptional.isPresent()) {
            voucherRepository.save(voucher);
            myResponse.setMessage("Edit new voucher successfully!");
        } else {
            myResponse.setMessage("This voucher code is not exist!!!");
            myResponse.setState("error");
            myResponse.setRspCode("400");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Voucher> fetchVoucherInfo(Long voucherId) {
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);
        return voucherOptional.map(voucher -> new ResponseEntity<>(voucher, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}
