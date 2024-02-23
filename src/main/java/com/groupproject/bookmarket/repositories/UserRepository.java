package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
