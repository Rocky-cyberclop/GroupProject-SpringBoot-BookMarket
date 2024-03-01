package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Page<Genre> findByNameLike(Pageable pageable, String name);
}
