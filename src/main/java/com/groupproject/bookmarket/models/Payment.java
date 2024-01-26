package com.groupproject.bookmarket.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private Long total;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;
}
