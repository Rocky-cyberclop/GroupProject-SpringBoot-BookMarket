package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
