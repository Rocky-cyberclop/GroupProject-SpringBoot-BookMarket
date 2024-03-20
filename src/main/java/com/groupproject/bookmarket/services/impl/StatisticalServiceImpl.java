package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndNumberOfOrder;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.responses.SimpleStatisticalResponse;
import com.groupproject.bookmarket.services.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private TestingRepository testingRepository;
    @Override
    public ResponseEntity<List<DateAndNumberOfBook>> getDateAndNumberOfBookStatistical() {
        List<DateAndNumberOfBook> result = orderRepository.statisticNumberOfBookSold(
                LocalDate.of(2022, 1, 1),
                LocalDate.now());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DateAndSales>> getSaleFromDateToDateStatistical() {
        List<DateAndSales> result = orderRepository.statisticSaleFromDateToDate(
                LocalDate.of(2022, 1, 1),
                LocalDate.now());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SimpleStatisticalResponse> getSimpleStatisticalInfo() {
        long totalBooks = bookRepository.findAll().size();
        long totalUsers = userRepository.findAll().size();
        long totalOrders = 0;
        long totalSales = 0;
        for (DateAndSales dateAndSales : orderRepository.statisticSaleFromDateToDate(LocalDate.of(2022, 1, 1),
                LocalDate.now())) {
            totalSales+= dateAndSales.getSales();
        }
        for (DateAndNumberOfOrder dateAndNumberOfOrder : orderRepository.statisticOrderFromDateToDate(
                LocalDate.of(2018, 1, 1),
                LocalDate.now())) {
            totalOrders += dateAndNumberOfOrder.getSold();
        }
        SimpleStatisticalResponse response = SimpleStatisticalResponse.builder()
                .totalBooks(totalBooks)
                .totalOrders(totalOrders)
                .totalSales(totalSales)
                .totalUsers(totalUsers)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
