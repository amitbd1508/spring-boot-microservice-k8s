package com.miniprojecttwo.paymentservice.DTO;

import lombok.Data;
import lombok.NonNull;
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
