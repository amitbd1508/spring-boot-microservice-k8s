package com.miniprojecttwo.orderservice.service;

import com.miniprojecttwo.orderservice.enums.OrderStatus;
import com.miniprojecttwo.orderservice.model.Order;
import com.miniprojecttwo.orderservice.model.OrderItem;
import com.miniprojecttwo.orderservice.model.dto.DeductProductRequest;
import com.miniprojecttwo.orderservice.repository.OrderItemRepository;
import com.miniprojecttwo.orderservice.repository.OrderRepository;
import com.miniprojecttwo.orderservice.security.AwesomeUserDetailsService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
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

    @Value("${product.url}")
    private String productUrl;

    @Value("${product.token}")
    private String productToken;
    @Value("${payment.token}")
    private String paymentToken;
    public Order addOrder(Order order){
        double totalprice  = 0.0;
       List<OrderItem> items=  order.getOrderItems();
       for(OrderItem item:items) {
           totalprice += item.getPrice() * item.getQuantity();
       }
       order.setTotalPrice(totalprice);
       return orderRepository.save(order);
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

        if(paymentResponse!=null && paymentResponse.equals("\"SUCCESS\"")){
            // reduce product



            List<DeductProductRequest> lists= new ArrayList<>();
            order.getOrderItems().forEach(item->{
                var tmp=new DeductProductRequest();
                tmp.setQuantity(item.getQuantity());
                tmp.setProductId(item.getProductId());
                lists.add(tmp);

            });

            var result = deductProductInventory(lists);
            //"PartiallyFailed": "Success"
            if(result!= null &&result.equals("PartiallyFailed")){
                var dborder = orderRepository.findById(order.getId()).orElse(null);
                dborder.setOrderStatus(OrderStatus.PARTIALLYFAILED);
                //dborder.setOrderItems(null);
                orderRepository.save(dborder);
                return dborder;
            }

            if(result!= null &&result.equals("Success")){
                var dborder = orderRepository.findById(order.getId()).orElse(null);
                dborder.setOrderStatus(OrderStatus.COMPLETED);
                //dborder.setOrderItems(null);
                orderRepository.save(dborder);

                return dborder;
            }

            var dborder = orderRepository.findById(order.getId()).orElse(null);
            dborder.setOrderStatus(OrderStatus.PAMENTCOMPLETED);
            //dborder.setOrderItems(null);
            orderRepository.save(dborder);
            return dborder;

        }else{
            var dborder = orderRepository.findById(order.getId()).orElse(null);
            dborder.setOrderStatus(OrderStatus.PARTIALLYFAILED);
            //dborder.setOrderItems(null);
            orderRepository.save(dborder);
            return dborder;
        }
    }

    private String deductProductInventory(List<DeductProductRequest> items){
        String url = this.productUrl+"/products/deduct-inventory";

        try {



            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("internal-token", productToken);
            headers.add("Content-Type","application/json");

            // create request
            //HttpEntity request = new HttpEntity(headers);
            var obj = JSONArray.toJSONString(items);
            var js = new JSONArray();
            items.forEach(it->{
                var tmp = new JSONObject();
                tmp.put("productId", it.getProductId());
                tmp.put("quantity", it.getQuantity());
                js.add(tmp);
            });

            var jss = js.toJSONString();

            HttpEntity<String> request =
                    new HttpEntity<String>(jss, headers);



            ResponseEntity<String> response = new RestTemplate().postForEntity(url,request, String.class);

            String json = response.getBody();
            return json;
        }catch (Exception ex){
            return null;

        }


    }





    private String makePayment(String paymentType, String userid, String orderId, double balance, String methodId){
        try {
          CircuitBreaker circuitBreaker = circuitBreakerFactory.create("make-payment-circuitbreaker");

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
            headers.add("internal-token", this.paymentToken);
            headers.add("Content-Type","application/json");

            // create request
            //HttpEntity request = new HttpEntity(headers);
            HttpEntity<String> request =
                    new HttpEntity<String>(body.toString(), headers);

//            ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);
//
//            String json = response.getBody();
//            return json;


            return circuitBreaker.run(() -> restTemplate.postForEntity(url,request, String.class)).getBody();


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
