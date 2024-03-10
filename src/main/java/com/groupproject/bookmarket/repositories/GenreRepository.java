package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Page<Genre> findByNameLikeIgnoreCase(Pageable pageable, String name);

    Optional<Genre> findByName(String name);

    List<Genre> findByBooksId(Long id);
}
