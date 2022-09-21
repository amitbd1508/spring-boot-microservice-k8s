package com.miniprojecttwo.orderservice.model.dto;

import lombok.Data;

@Data
public class DeductProductRequest {
    private Long productId;
    private int quantity;
}
