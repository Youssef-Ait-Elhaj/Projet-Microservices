package com.emsi.inventoryservice.web;

import com.emsi.inventoryservice.entity.Product;
import com.emsi.inventoryservice.repository.ProductRepository;
import com.emsi.inventoryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {
        return productRepository.findById(productId).get();
    }

    @PostMapping("/products")
    public Product publish(@RequestBody Product product) {
        Product product1 =  productRepository.save(product);
        streamBridge.send("PRODUCT_TOPIC", product1);
        return product1;
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody Product newProduct) {
        newProduct.setId(productId);
        return productRepository.save(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long productId) {
        productRepository.deleteById(productId);
    }
}
