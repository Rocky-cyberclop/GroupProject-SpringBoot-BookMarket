package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
