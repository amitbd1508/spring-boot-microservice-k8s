package com.miniprojecttwo.orderservice.service;

import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.repository.OrderItemRepository;
import com.miniprojecttwo.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order addOrder(Order order){
        double totalprice  = 0.0;
       List<OrderItem> items=  order.getOrderItems();
       for(OrderItem item:items) {
           totalprice += item.getPrice();
       }
       order.setTotalPrice(totalprice);
       return orderRepository.save(order);
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        return orderRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid id : "+id));
    }

    public OrderItem addOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItems(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(int id){
        return orderItemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid id : "+id));
    }

    public Order save(Order order) {
        double totalprice  = 0.0;
        for (OrderItem p : order.getOrderItems()) {


            OrderItem item = restTemplate.getForObject(orderItemService.getProductUri()+p.getProductId(), OrderItem.class);
            System.out.println(item.getPrice());
            System.out.println(p.getQuantity());
            if(item.getQuantity() < p.getQuantity()) {
                return null;
            }
            totalprice += p.getQuantity() * item.getPrice();
            p.setPrice(item.getPrice());
        }
        orderItemService.saveAll(order.getOrderItems());
        order.setTotalPrice(totalprice);
        orderRepository.save(order);
        return order;
    }
}
