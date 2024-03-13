package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Page<UserDto> findByFullNameLikeIgnoreCaseOrEmailLikeIgnoreCaseOrAddressLikeIgnoreCase(Pageable pageable, String fullName, String Email, String address);
    Optional<User> findByEmail(String email);
}
