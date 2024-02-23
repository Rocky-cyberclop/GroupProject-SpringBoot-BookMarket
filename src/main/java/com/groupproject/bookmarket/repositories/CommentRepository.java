package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
