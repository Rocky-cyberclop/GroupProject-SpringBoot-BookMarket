package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
