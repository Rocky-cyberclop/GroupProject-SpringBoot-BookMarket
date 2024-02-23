package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
