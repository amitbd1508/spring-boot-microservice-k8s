package com.miniprojecttwo.productservice.service;

import com.miniprojecttwo.productservice.dto.DeductInventoryRequest;
import com.miniprojecttwo.productservice.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
  List<Product> getAll();
  Product get(Long id);
  Product save(Product product);
  Product deduct(Long id, Long quantity);
  Product add(Long id, Long quantity);

  String deductInventory(List<DeductInventoryRequest> products);
}
