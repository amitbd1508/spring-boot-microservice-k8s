package com.miniprojecttwo.orderservice.repository;

import com.miniprojecttwo.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
