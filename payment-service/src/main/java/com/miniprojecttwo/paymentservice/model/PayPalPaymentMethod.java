package com.miniprojecttwo.paymentservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PayPalPaymentMethod implements PaymentMethods{
    private final String serviceAddress;
    private final Payment payment;

    public PayPalPaymentMethod(String serviceAddress, Payment payment) {
        this.serviceAddress = serviceAddress;
        this.payment = payment;
    }

    @Override
    public String getPaymentUri() {
        return serviceAddress;
    }

    @Override
    public PaymentTypes getType() {
        return PaymentTypes.PAYPAL;
    }
}
