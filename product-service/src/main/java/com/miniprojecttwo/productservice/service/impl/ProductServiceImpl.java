package com.miniprojecttwo.productservice.service.impl;

import com.miniprojecttwo.productservice.dto.DeductInventoryRequest;
import com.miniprojecttwo.productservice.entity.Product;
import com.miniprojecttwo.productservice.repository.ProductRepository;
import com.miniprojecttwo.productservice.service.ProductService;
import com.miniprojecttwo.productservice.util.EntityNotFoundException;
import com.miniprojecttwo.productservice.util.EntityUpdateFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public Product get(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
  }

  @Override
  public Product save(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product add(Long productId, Long quantity) {
    Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId + " not found"));
    if(product != null) {
      product.setQuantity(product.getQuantity() + quantity);
      productRepository.save(product);
    }
    return product;
  }

  @Override
  public String deductInventory(List<DeductInventoryRequest> products) {
    boolean partiallyFailed = false;
    for(DeductInventoryRequest p: products) {
      try{
        deduct(p.getProductId(), p.getQuantity());
      } catch (Exception ex) {
        partiallyFailed = true;
      }
    }
    return partiallyFailed ? "PartiallyFailed": "Success";
  }

  @Override
  public Product deduct(Long productId, Long quantity) {
    Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId + " not found"));
    if(product.getQuantity() - quantity < 0) throw new EntityUpdateFailedException("Product quantity cannot be negative");
    product.setQuantity(product.getQuantity() - quantity);
    productRepository.save(product);

    //TODO: We will call the notification service to send notification to vendor
    if(product.getQuantity() <= product.getThresholdQuantity()) {
      System.out.println("Threshold exceeded warning: "+product.getName()+" ( " + product.getId() + ")" + " is exceed the threshold quantity");
    }
    return product;
  }
}
