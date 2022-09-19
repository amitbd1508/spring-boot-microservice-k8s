package com.miniprojecttwo.paymentservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "payment_uuid", nullable = false)
    private String uuid;
    private PaymentTypes paymentType;
    private String orderId;
    private String transactionId;
    private PaymentStatus status;

    public OrderPayment(){
        if(uuid == null)
            this.uuid = UUID.randomUUID().toString();
    }


}
