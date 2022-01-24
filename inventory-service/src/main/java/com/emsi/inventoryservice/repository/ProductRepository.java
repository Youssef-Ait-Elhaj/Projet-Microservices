package com.emsi.inventoryservice.repository;

import com.emsi.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    PagedModel<Product> pageProducts();
}
