package com.miniprojecttwo.paymentservice.DTO;

import lombok.Data;
import lombok.NonNull;
@Data
public class BankAccountRequest {

    @NonNull
    private String userId;

    @NonNull
    private String bankId;

    @NonNull
    private Double balance;

    @NonNull
    public String orderId;

}
