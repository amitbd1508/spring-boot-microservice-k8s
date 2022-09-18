package com.miniprojecttwo.productservice.service.impl;

import com.miniprojecttwo.productservice.entity.Product;
import com.miniprojecttwo.productservice.repository.ProductRepository;
import com.miniprojecttwo.productservice.service.ProductService;
import com.miniprojecttwo.productservice.util.EntityNotFoundException;
import com.miniprojecttwo.productservice.util.ProductMessage;
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
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id+ " not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product add(Long productId, Long quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId+ " not found"));;
        //ProductMessage response = product.add(quantity);
        //Product productDTO = MapperUtil.map(productRepository.save(product), Product.class);
        return product;
    }

    @Override
    public Product deduct(Long productId, Long quantity) {
        Product product = productRepository.findById(productId).get();
        ProductMessage response = product.deduct(quantity);
        if(response.equals(ProductMessage.ERROR)) return null;
        //if(response == ProductMessage.BELOW_THRESHOLD){
          //  log.info("sending message [" + "Deduct stock from: " + product.getName() + "] to " + stockUrl);
            //restTemplate.postForEntity(stockUrl, "Deduct stock from: " + product.getName(), String.class);
       // }
        return product;
    }
}
