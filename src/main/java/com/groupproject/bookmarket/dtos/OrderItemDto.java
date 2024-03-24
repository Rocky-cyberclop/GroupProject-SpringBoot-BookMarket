package com.groupproject.bookmarket.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDto {

        private Long id;

        private Integer quantity;

        private Long price;

        private BookDto book;

        private Order order;

}
