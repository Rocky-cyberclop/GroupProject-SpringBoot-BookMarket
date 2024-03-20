package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.dtos.DateAndNumberOfBook;
import com.groupproject.bookmarket.dtos.DateAndNumberOfOrder;
import com.groupproject.bookmarket.dtos.DateAndSales;
import com.groupproject.bookmarket.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT payment.date as date, sum(receipt_item.quantity) as sumBook\n" +
            "\tFROM receipt, receipt_item, payment\n" +
            "\twhere receipt.id=receipt_item.order_id and payment.order_id=receipt.id\n" +
            "\tand payment.date>=?1 and payment.date<=?2\n" +
            "\tgroup by payment.date\n" +
            "\torder by payment.date;", nativeQuery = true)
    List<DateAndNumberOfBook> statisticNumberOfBookSold(LocalDate from, LocalDate to);

    @Query(value = "SELECT payment.date as date, sum(payment.total) as sales\n" +
            "\tFROM receipt, receipt_item, payment\n" +
            "\twhere receipt.id=receipt_item.order_id and payment.order_id=receipt.id\n" +
            "\tand payment.date>=?1 and payment.date<=?2\n" +
            "\tgroup by payment.date\n" +
            "\torder by payment.date;", nativeQuery = true)
    List<DateAndSales> statisticSaleFromDateToDate(LocalDate from, LocalDate to);

    @Query(value = "SELECT payment.date as date, count(receipt.id) as sold\n" +
            "\tFROM receipt, payment\n" +
            "\twhere payment.order_id=receipt.id\n" +
            "\tand payment.date>=?1 and payment.date<=?2\n" +
            "\tgroup by payment.date\n" +
            "\torder by payment.date;", nativeQuery = true)
    List<DateAndNumberOfOrder> statisticOrderFromDateToDate(LocalDate from, LocalDate to);

    List<Order> findByUserId(Long userId);
    Page<Order> findByAddressLikeOrStatusLike(Pageable pageable, String address, String status);
}
