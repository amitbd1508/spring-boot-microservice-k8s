package com.miniprojecttwo.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private String city;
    private String state;
    private String streetNumber;
    private String zip;
}
