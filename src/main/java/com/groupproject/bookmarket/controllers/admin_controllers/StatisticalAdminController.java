package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.responses.SimpleStatisticalResponse;
import com.groupproject.bookmarket.services.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/statistical")
public class StatisticalAdminController {
    @Autowired
    private StatisticalService statisticalService;

    @GetMapping("/dateAndNumberOfBook")
    public ResponseEntity<List<DateAndNumberOfBook>> statisticalDateAndNumberOfBook() {
        return statisticalService.getDateAndNumberOfBookStatistical();
    }

    @GetMapping("/saleFromDateToDate")
    public ResponseEntity<List<DateAndSales>> statisticalSaleFromDateToDate() {
        return statisticalService.getSaleFromDateToDateStatistical();
    }

    @GetMapping("/simpleStatistical")
    public ResponseEntity<SimpleStatisticalResponse> getTotal() {
        return statisticalService.getSimpleStatisticalInfo();
    }
}
