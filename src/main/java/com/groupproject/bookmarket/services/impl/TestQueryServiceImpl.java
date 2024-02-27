package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndNumberOfOrder;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.repositories.OrderRepository;
import com.groupproject.bookmarket.services.TestQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class TestQueryServiceImpl implements TestQueryService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
//    @Bean
    public void testQuerySelectBooksFromFirstDateOfMonthToNow() {
//        for (DateAndNumberOfBook dateAndNumberOfBook : orderRepository.statisticNumberOfBookSold(
//                LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
//                LocalDate.now())) {
//            System.out.println((dateAndNumberOfBook.getDate()));
//            System.out.println((dateAndNumberOfBook.getSumBook()));
//        }//This block of code will print no data cause there's no data related in database
        for (DateAndNumberOfBook dateAndNumberOfBook : orderRepository.statisticNumberOfBookSold(
                LocalDate.of(2018, 1, 1),
                LocalDate.of(2018, 1, 12))) {
            System.out.println((dateAndNumberOfBook.getDate()));
            System.out.println((dateAndNumberOfBook.getSumBook()));
        }
    }

    @Override
//    @Bean
    public void testQuerySelectSalesFromFirstDateOfMonthToNow() {
//        for (DateAndSales dateAndSales : orderRepository.statisticSaleFromDateToDate(
//        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
//                LocalDate.now())) {
//            System.out.println((dateAndSales.getDate()));
//            System.out.println((dateAndSales.getSales()));
//        }//This block of code will print no data cause there's no data related in database

        for (DateAndSales dateAndSales : orderRepository.statisticSaleFromDateToDate(
                LocalDate.of(2018,1,1),
                LocalDate.of(2023,1,1))) {
            System.out.println((dateAndSales.getDate()));
            System.out.println((dateAndSales.getSales()));
        }
    }

    @Override
//    @Bean
    public void testQuerySelectOrdersFromFirstDateOfMonthToNow() {
//        for (DateAndNumberOfOrder dateAndNumberOfOrder : orderRepository.statisticOrderFromDateToDate(
//        LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),
//                LocalDate.now())) {
//            System.out.println((dateAndNumberOfOrder.getDate()));
//            System.out.println((dateAndNumberOfOrder.getSold()));
//        }//This block of code will print no data cause there's no data related in database

        for (DateAndNumberOfOrder dateAndNumberOfOrder : orderRepository.statisticOrderFromDateToDate(
                LocalDate.of(2018,1,1),
                LocalDate.of(2020,5,6))) {
            System.out.println((dateAndNumberOfOrder.getDate()));
            System.out.println((dateAndNumberOfOrder.getSold()));
        }
    }


    @Override
//    @Bean
    public void testQuerySelectSalesFromTo(/*LocalDate from, LocalDate to*/) {
        for (DateAndSales dateAndSales : orderRepository.statisticSaleFromDateToDate(LocalDate.of(2018, 1, 1),
                LocalDate.of(2022, 1, 12))) {
            System.out.println((dateAndSales.getDate()));
            System.out.println((dateAndSales.getSales()));
        }
    }
}
