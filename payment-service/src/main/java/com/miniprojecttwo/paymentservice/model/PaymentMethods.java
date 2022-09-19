package com.miniprojecttwo.paymentservice.model;

public interface PaymentMethods {
   Payment getPayment();
    String getPaymentUri();
    PaymentTypes getType();
}
