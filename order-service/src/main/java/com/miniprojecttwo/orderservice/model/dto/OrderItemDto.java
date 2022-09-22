package com.miniprojecttwo.orderservice.model.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class OrderItemDto {
    private Long productId;

    private int quantity;
}
