package com.miniprojecttwo.orderservice.service;

import com.miniprojecttwo.orderservice.enums.OrderStatus;
import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.repository.OrderItemRepository;
import com.miniprojecttwo.orderservice.repository.OrderRepository;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import com.miniprojecttwo.orderservice.util.UserUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
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

        var user= awesomeUserDetailsService.getAccount();

        var paymentResponse =makePayment(user.getPreferredPaymentMethod(),order.getUserId().toString(),order.getId().toString(), order.getTotalPrice(), "12345678"  );

        if(paymentResponse== "SUCCESS"){
            // reduce product
            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
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

            if(paymentType=="paypal"){
                purl="paypal";
                body.put("paypalId",methodId);

            }


            if(paymentType =="credit card"){
                purl="creditcard";
                body.put("creditCardNumber",methodId);
            }

            if(paymentType=="debit card"){
                purl="bankaccount";
                body.put("bankId",methodId);
            }

            String url = this.paymentUrl+"/payment/"+paymentUrl;

            // create auth credentials
            String token = "jwttoken";


            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

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
