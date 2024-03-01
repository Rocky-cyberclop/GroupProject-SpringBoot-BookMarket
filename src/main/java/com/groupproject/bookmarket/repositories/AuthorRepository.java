package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByNameLike(Pageable pageable, String name);
}
