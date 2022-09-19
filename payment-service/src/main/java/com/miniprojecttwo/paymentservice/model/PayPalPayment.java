package com.miniprojecttwo.paymentservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayPalPayment implements Payment {
    private String userId;
    private String paypalId;
    private Double balance;
}
