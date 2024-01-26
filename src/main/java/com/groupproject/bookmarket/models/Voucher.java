package com.groupproject.bookmarket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Float percent;

    private Float highestRate;

    private Integer quantity;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucher", orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
