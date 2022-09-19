package com.miniprojecttwo.orderservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
public class Address {

    private String city;
    private String state;
    private String streetNumber;
    private String zip;
}
