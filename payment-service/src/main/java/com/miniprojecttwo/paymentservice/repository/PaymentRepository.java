package com.miniprojecttwo.paymentservice.repository;

import com.miniprojecttwo.paymentservice.model.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<OrderPayment, Long> {
}
