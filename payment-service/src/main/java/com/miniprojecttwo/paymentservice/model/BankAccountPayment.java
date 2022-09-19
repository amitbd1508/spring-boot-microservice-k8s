package com.miniprojecttwo.paymentservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountPayment implements Payment {

    private String userId;
    private String accountId;
    private Double balance;

}
