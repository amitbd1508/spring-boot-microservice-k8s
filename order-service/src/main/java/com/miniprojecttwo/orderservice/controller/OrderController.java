package com.miniprojecttwo.orderservice.controller;


import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/item")
    public OrderItem addOrderItem(@RequestBody OrderItem orderItem){
        return service.addOrderItem(orderItem);
    }

    @GetMapping
    public List<Order> getOrders(){
        return service.getOrders();
    }

    @GetMapping("/item")
    public List<OrderItem> getOrderItems(){
        return service.getOrderItems();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id){
        return service.getOrderById(id);
    }

    @GetMapping("/item/{id}")
    public OrderItem getOrderItemById(@PathVariable int id){
        return service.getOrderItemById(id);
    }

    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        Order order1 = service.save(order);
        if(order1 == null) return new ResponseEntity<>("Order create failed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(order1, HttpStatus.OK);
    }
}
