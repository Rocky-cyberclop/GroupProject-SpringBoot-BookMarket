package com.groupproject.bookmarket.repositories;

import com.groupproject.bookmarket.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
