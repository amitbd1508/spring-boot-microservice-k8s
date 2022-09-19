package com.miniprojecttwo.paymentservice.model;

import lombok.Data;

@Data
public class CreditCardPayment implements Payment{
    private String userId;
    private String creditCardNumber;
    private Double balance;
}
