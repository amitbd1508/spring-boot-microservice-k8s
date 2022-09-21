package com.miniprojecttwo.productservice.controller;

import com.miniprojecttwo.productservice.dto.DeductInventoryRequest;
import com.miniprojecttwo.productservice.entity.Product;
import com.miniprojecttwo.productservice.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Api(tags = "Products")
public class ProductController {

  ProductService productService;

  @Autowired
  ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping()
  public ResponseEntity<List<Product>> getProducts() {
    return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProduct(@PathVariable Long id) {
    return new ResponseEntity<>(productService.get(id), HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
    return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
  }

  @PutMapping("/{id}/deduct/{quantity}")
  public ResponseEntity<?> deductProductQuantity(@PathVariable Long id, @PathVariable Long quantity) {
    Product productDTO = productService.deduct(id, quantity);
    if (productDTO == null)
      return new ResponseEntity<>("The requested quantity is beyond the stock on hand.", HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(productDTO, HttpStatus.OK);
  }

  @PutMapping("/{id}/add/{quantity}")
  public ResponseEntity<Product> addProductQuantity(@PathVariable Long id, @PathVariable Long quantity) {
    return new ResponseEntity<>(productService.add(id, quantity), HttpStatus.OK);
  }

  @PutMapping("/deduct-inventory")
  public ResponseEntity<String> deductInventory(@RequestBody List<DeductInventoryRequest> products){
    return new ResponseEntity<>(productService.deductInventory(products), HttpStatus.OK);
  }

}
