package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    Page<Book> findLikeTitleBook(Pageable pageable, String title);

    Page<Book> findByTitleLikeAndIsDeleteFalse(Pageable pageable, String title);
}
