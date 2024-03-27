package com.groupproject.bookmarket.dtos;

import com.groupproject.bookmarket.models.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PaymentDto {

    private String code;

    private LocalDate date;

    private Long total;
}
