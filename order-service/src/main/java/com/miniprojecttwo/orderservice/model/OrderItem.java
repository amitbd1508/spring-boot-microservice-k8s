package com.miniprojecttwo.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "items_generator")
    private Integer id;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

//    public OrderItem(){}
//
//    public OrderItem(Order order, Integer productId, int quantity, double price) {
//        this.order = order;
//        this.productId = productId;
//        this.quantity = quantity;
//        this.price = price;
//    }
//
//
//    public Integer getOrderItemId() {
//        return id;
//    }
//
//    public void setOrderItemId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Integer productId) {
//        this.productId = productId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

}
