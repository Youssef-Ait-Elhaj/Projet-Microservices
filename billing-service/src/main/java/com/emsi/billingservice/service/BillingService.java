package com.emsi.billingservice.service;

import com.emsi.billingservice.entities.Customer;
import com.emsi.billingservice.entities.ProductItem;
import com.emsi.billingservice.entities.Product;
import com.emsi.billingservice.model.ProductModel;
import com.emsi.billingservice.repository.CustomerRepository;
import com.emsi.billingservice.repository.ProductItemRepository;
import com.emsi.billingservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class BillingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Bean
    public Consumer<Product> productConsumer() {
        return (input) -> {
            ProductModel productModel = new ProductModel(null, input.getName(), input.getPrice(), input.getQuantity());
            // create and save product item from added product
            System.out.println("id: " + input.getId());
            ProductItem productItem = new ProductItem(null, input.getQuantity(), input.getPrice(), input.getId(),
                    null, productModel, input.getName());
            productItemRepository.save(productItem);

            // save product
            productRepository.save(input);
        };
    }

    @Bean
    public Consumer<Customer> customerConsumer() {
        return (input) -> {
            customerRepository.save(input);
        };
    }
}
