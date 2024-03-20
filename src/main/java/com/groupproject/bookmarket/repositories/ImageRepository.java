package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBookId(Long id);
}
