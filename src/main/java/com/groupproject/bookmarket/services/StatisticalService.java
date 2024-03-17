package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.responses.SimpleStatisticalResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StatisticalService {
    ResponseEntity<List<DateAndNumberOfBook>> getDateAndNumberOfBookStatistical();

    ResponseEntity<List<DateAndSales>> getSaleFromDateToDateStatistical();

    ResponseEntity<SimpleStatisticalResponse> getSimpleStatisticalInfo();
}
