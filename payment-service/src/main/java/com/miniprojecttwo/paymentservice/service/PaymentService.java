package com.miniprojecttwo.paymentservice.service;

import com.miniprojecttwo.paymentservice.model.OrderPayment;
import com.miniprojecttwo.paymentservice.model.PaymentMethods;
import com.miniprojecttwo.paymentservice.model.PaymentStatus;
import com.sun.istack.NotNull;

public interface PaymentService {
    PaymentStatus makePayment(PaymentMethods paymentMethods, @NotNull String orderid);

    OrderPayment findPayment(Long id);
}
