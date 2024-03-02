package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    Page<Book> findLikeTitleBook(Pageable pageable, String title);

    Optional<Book> findByTitle(String title);

    Page<Book> findByTitleLikeAndIsDeleteFalse(Pageable pageable, String title);
}
