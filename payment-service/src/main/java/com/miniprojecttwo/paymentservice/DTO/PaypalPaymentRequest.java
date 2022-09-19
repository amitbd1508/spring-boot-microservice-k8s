package com.miniprojecttwo.paymentservice.DTO;

import lombok.Data;
import lombok.NonNull;

@Data
public class PaypalPaymentRequest {
     @NonNull
    private String userId;

    @NonNull
    private String paypalId;

    @NonNull
    private Double balance;

    @NonNull
    public String orderId;

}