package com.miniprojecttwo.orderservice.model;

import com.miniprojecttwo.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
//
//    public Order() {
//    }
//
//    public Order(Integer id, Integer userId, Double totalPrice, List<OrderItem> orderItems) {
//        this.id = id;
//        this.userId = userId;
//        this.totalPrice = totalPrice;
//        this.orderItems = orderItems;
//    }
//
//    public Integer getOrderId() {
//        return id;
//    }
//
//    public void setOrderId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
////    public Date getCreatedDate() {
////        return createdDate;
////    }
////
////    public void setCreatedDate(Date createdDate) {
////        this.createdDate = createdDate;
////    }
//
//    public Double getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(Double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItem> orderItems) {
//        this.orderItems = orderItems;
//    }
}
