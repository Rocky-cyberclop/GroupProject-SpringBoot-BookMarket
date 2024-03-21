package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.dtos.VnpPaymentDTO;
import com.groupproject.bookmarket.responses.MessageResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ConfigPaymenntService {

    @Cacheable(value = "TxRef", key = "#username")
    ResponseEntity<MessageResponse> createUrlPayment(String username, Double totalPrice) throws UnsupportedEncodingException;


    ResponseEntity<?> handlePaymentResult(@RequestBody VnpPaymentDTO requestData, String email);
}
