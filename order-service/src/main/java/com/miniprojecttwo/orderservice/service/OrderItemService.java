package com.miniprojecttwo.orderservice.service;

import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Value("${product.url}")
    private String productUri;

    @Autowired
    private OrderItemRepository repository;

    public String getProductUri() {
        return this.productUri;
    }


    public List<OrderItem> saveAll(List<OrderItem> items) {
        return repository.saveAll(items);
    }
}
