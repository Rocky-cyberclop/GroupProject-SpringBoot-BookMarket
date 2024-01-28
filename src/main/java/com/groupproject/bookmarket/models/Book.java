package com.groupproject.bookmarket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can not be blank")
    @Column(columnDefinition = "text")
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String language;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate publishDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate lastUpdate;

    @Positive(message = "Price must be positive")
    private Long price;

    private Integer quantity;

    private Boolean isDelete;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnore
    private List<Author> authors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnore
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    @JsonIgnore
    private List<Image> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "book_cartItem",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "cartItem_id"))
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

}
