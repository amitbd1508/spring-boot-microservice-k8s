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

}
