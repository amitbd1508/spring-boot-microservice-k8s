package com.miniprojecttwo.paymentservice.service.impl;

import com.miniprojecttwo.paymentservice.model.OrderPayment;
import com.miniprojecttwo.paymentservice.model.PaymentMethods;
import com.miniprojecttwo.paymentservice.model.PaymentStatus;
import com.miniprojecttwo.paymentservice.repository.PaymentRepository;
import com.miniprojecttwo.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentStatus makePayment(PaymentMethods paymentMethods, String orderId ) {
        log.info("sending request to: " + paymentMethods.getPaymentUri());

//        ResponseEntity<String> result = restTemplate.postForEntity(
//                paymentMethods.getPaymentUri(), paymentMethods.getPayment(), String.class
//        );

//        if(result.getStatusCode() != HttpStatus.OK){
//            //wrap into some responses message
//            log.error("error has occurred!");
//            return PaymentStatus.ERROR;
//        }
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setPaymentType(paymentMethods.getType());
        orderPayment.setOrderId(orderId);
        orderPayment.setStatus(PaymentStatus.SUCCESS);
      //  orderPayment.setTransactionId(result.getBody());

        paymentRepository.save(orderPayment);

        log.info("Payment Processed and saved!");

        return PaymentStatus.SUCCESS;
    }

    @Override
    public OrderPayment findPayment(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No order payment"));
    }
}
