package com.miniprojecttwo.paymentservice.model;

public class BankAccountPaymentMethod implements PaymentMethods{
    private  final String serviceAddress;
    private Payment payment;

    public BankAccountPaymentMethod(String serviceAddress, Payment payment) {
        this.serviceAddress = serviceAddress;
        this.payment = payment;
    }

    @Override
    public Payment getPayment() {
        return null;
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
