package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndNumberOfOrder;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.models.*;
import com.groupproject.bookmarket.repositories.*;
import com.groupproject.bookmarket.services.TestQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestQueryServiceImpl implements TestQueryService {

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
                LocalDate.of(2018, 1, 1),
                LocalDate.of(2023, 1, 1))) {
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
                LocalDate.of(2018, 1, 1),
                LocalDate.of(2020, 5, 6))) {
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

    //    @Bean
    public void makeIdEqually() {
        Author author = new Author();
        int i = 0;
        while (i < 1327) {
            try {
                authorRepository.save(author);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

    }

//    @Bean
    public void importFromCsv() {
        List<Testing> testings = new ArrayList<>();
        String filePath = "F:\\test.csv"; // Replace this with your actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Testing testing = new Testing();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    testing.setStatus(parts[1].trim());
                }
                testings.add(testing);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        testingRepository.saveAll(testings);
    }

}
