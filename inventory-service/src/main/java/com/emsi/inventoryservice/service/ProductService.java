package com.emsi.inventoryservice.service;

import com.emsi.inventoryservice.entity.Product;
import com.emsi.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.util.function.Supplier;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Supplier<Product> productSupplier(Product product) {
        return () -> product;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }
}
