package com.miniprojecttwo.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
@AllArgsConstructor
@Data
public class CreditCardRequest {
    @NonNull
    private String userId;

    @NonNull
    public String creditCardNumber;

    @NonNull
    public Double balance;

    @NonNull
    public String orderId;

}
