package com.miniprojecttwo.orderservice.model.dto;

import com.miniprojecttwo.orderservice.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private List<OrderItemDto> orderItems;
}
