package com.miniprojecttwo.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;

    @Embedded
    private Address shippingAddress;

    private String preferredPaymentMethod;


}
