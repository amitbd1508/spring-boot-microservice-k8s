package com.miniprojecttwo.orderservice.controller;
import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import com.miniprojecttwo.orderservice.service.OrderService;
import com.miniprojecttwo.orderservice.util.UserUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Api( tags = "Order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    AwesomeUserDetailsService awesomeUserDetailsService;
    @PostMapping()
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        UserUtil.getLoggedInUserId();

        var acc = awesomeUserDetailsService.getAccount();
        Order order1 = service.save(order);
        if(order1 == null) return new ResponseEntity<>("Order create failed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(order1, HttpStatus.OK);
    }
}
