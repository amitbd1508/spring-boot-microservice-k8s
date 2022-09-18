package com.miniprojecttwo.productservice.entity;

import com.miniprojecttwo.productservice.util.Category;
import com.miniprojecttwo.productservice.util.ProductMessage;
import com.miniprojecttwo.productservice.util.ProductOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String vendor;
    private Long quantity;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;

    private Long thresholdQuantity;

    public ProductMessage deduct(Long quantity) {
        if(this.quantity > quantity) {
            this.quantity -= quantity;
            return checkThreshold(ProductOperation.DEDUCT);
        }
        return ProductMessage.ERROR;
    }
    public ProductMessage add(Long quantity) {
        this.quantity += quantity;
        return checkThreshold(ProductOperation.ADD);
    }

    private ProductMessage checkThreshold(ProductOperation operation) {
        if(this.quantity < thresholdQuantity) {
            log.info("Product [" + this.id + "] is below a minimum threshold.");
            return ProductMessage.BELOW_THRESHOLD;
        }
        if(this.quantity >= thresholdQuantity && operation.equals(ProductOperation.ADD)) {
            log.info("Product [" + this.id + "] is restocked.");
            return ProductMessage.RESTOCKED;
        }
        return ProductMessage.NONE;
    }
}
