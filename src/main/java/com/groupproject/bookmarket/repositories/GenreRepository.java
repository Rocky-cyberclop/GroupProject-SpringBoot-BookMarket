package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
