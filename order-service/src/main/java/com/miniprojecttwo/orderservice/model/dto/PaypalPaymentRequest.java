package com.miniprojecttwo.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
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