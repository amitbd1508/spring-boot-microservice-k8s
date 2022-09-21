package com.miniprojecttwo.orderservice.service;

import com.miniprojecttwo.orderservice.enums.OrderStatus;
import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.repository.OrderItemRepository;
import com.miniprojecttwo.orderservice.repository.OrderRepository;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Autowired
    AwesomeUserDetailsService awesomeUserDetailsService;

    @Value("${payment.url}")
    private String paymentUrl;
    public Order addOrder(Order order){
        double totalprice  = 0.0;
       List<OrderItem> items=  order.getOrderItems();
       for(OrderItem item:items) {
           totalprice += item.getPrice() * item.getQuantity();
       }
       order.setTotalPrice(totalprice);
       return orderRepository.save(order);
    }

    public List<Order> getOrders(){

        return StreamSupport.stream(orderRepository.findAll().spliterator(), false).collect(Collectors.toList());

    }

    public Order getOrderById(int id){
        return orderRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid id : "+id));
    }

    public OrderItem addOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItems(){
        return StreamSupport.stream(orderItemRepository.findAll().spliterator(), false).collect(Collectors.toList());

    }

    public OrderItem getOrderItemById(int id){
        return orderItemRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid id : "+id));
    }

    public Order save(Order order) {
        double totalprice  = 0.0;
        var user= awesomeUserDetailsService.getAccount();

        order.setUserId(user.getId().intValue());
        for (OrderItem p : order.getOrderItems()) {


            OrderItem item = restTemplate.getForObject(orderItemService.getProductUri()+"/products/"+p.getProductId(), OrderItem.class);
            //System.out.println(item.getPrice());
            //System.out.println(p.getQuantity());
            if(item.getQuantity() < p.getQuantity()) {
                return null;
            }
            totalprice += p.getQuantity() * item.getPrice();
            p.setPrice(item.getPrice());
        }
        //orderItemService.saveAll(order.getOrderItems());
        order.setTotalPrice(totalprice);
        orderRepository.save(order);



        var paymentResponse =makePayment(user.getPreferredPaymentMethod(),order.getUserId().toString(),order.getId().toString(), order.getTotalPrice(), "12345678"  );

        if(paymentResponse.equals("\"SUCCESS\"")){
            // reduce product

            var dborder = orderRepository.findById(order.getId()).orElse(null);
            dborder.setOrderStatus(OrderStatus.COMPLETED);
            //dborder.setOrderItems(null);
            orderRepository.save(dborder);
        }
        return order;
    }



    private String makePayment(String paymentType, String userid, String orderId, double balance, String methodId){
        try {
            String purl = "";

            var body = new JSONObject();
            body.put("balance",balance);
            body.put("orderId",orderId);
            body.put("userId",userid);

            if(paymentType.equals("paypal")){
                purl="paypal";
                body.put("paypalId",methodId);

            }


            if(paymentType.equals("credit card")){
                purl="creditcard";
                body.put("creditCardNumber",methodId);
            }

            if(paymentType.equals("debit card")){
                purl="bankaccount";
                body.put("bankId",methodId);
            }

            String url = this.paymentUrl+"/payment/"+purl;

            // create auth credentials
            String token = "jwttoken";


            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.add("Content-Type","application/json");

            // create request
            //HttpEntity request = new HttpEntity(headers);
            HttpEntity<String> request =
                    new HttpEntity<String>(body.toString(), headers);

            ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);

            String json = response.getBody();
            return json;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
