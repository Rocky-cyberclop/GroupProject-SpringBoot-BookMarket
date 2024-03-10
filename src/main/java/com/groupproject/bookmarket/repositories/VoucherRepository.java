package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Page<Voucher> findByCodeLikeIgnoreCase(Pageable pageable, String code);

    Optional<Voucher> findByCode(String code);
}
