package com.miniprojecttwo.orderservice.repository;

import com.miniprojecttwo.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
