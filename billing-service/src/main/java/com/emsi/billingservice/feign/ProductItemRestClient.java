package com.emsi.billingservice.feign;

import com.emsi.billingservice.model.ProductModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductItemRestClient {

    @GetMapping("/products")
    PagedModel<ProductModel> pageProducts();

    @GetMapping(path = "/products")
    PagedModel<ProductModel> pageProducts(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size);

    @GetMapping(path = "/products/{id}")
    ProductModel getProductById(@PathVariable Long id);
}
