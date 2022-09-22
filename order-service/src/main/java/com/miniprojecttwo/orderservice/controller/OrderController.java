package com.miniprojecttwo.orderservice.controller;
import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.model.dto.OrderDto;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import com.miniprojecttwo.orderservice.service.OrderService;
import com.miniprojecttwo.orderservice.util.UserUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Api( tags = "Order")
public class OrderController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private OrderService service;

    @Autowired
    AwesomeUserDetailsService awesomeUserDetailsService;
    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody OrderDto order) {

        //var orderEntity = modelMapper.map(order, Order.class);


        List<OrderItem> items = new ArrayList<>();

        if(order!= null && order.getOrderItems()!= null){
            order.getOrderItems().forEach(i->{
                OrderItem tmp = new OrderItem();
                tmp.setQuantity(i.getQuantity());
                tmp.setProductId(i.getProductId());
                items.add(tmp);
            });

            Order ord= new Order();
            ord.setOrderItems(items);

            Order order1 = service.save(ord);
            if(order1 == null) return new ResponseEntity<>("Order create failed", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(order1, HttpStatus.OK);

        }

        return new ResponseEntity<>("Not a valid order",HttpStatus.OK);


    }
}
