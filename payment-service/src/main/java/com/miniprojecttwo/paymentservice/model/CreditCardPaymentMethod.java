package com.miniprojecttwo.paymentservice.model;

import lombok.*;

@Getter
@Setter
public class CreditCardPaymentMethod implements PaymentMethods {
private final String serviceAddress;
private final Payment payment;

    public CreditCardPaymentMethod(String serviceAddress, Payment payment) {
        this.serviceAddress = serviceAddress;
        this.payment = payment;
    }

    @Override
    public String getPaymentUri() {
        return serviceAddress;
    }
    @Override
    public PaymentTypes getType() {
        return PaymentTypes.CREDITCARD;
    }
}
