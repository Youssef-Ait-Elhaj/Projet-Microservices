package com.emsi.billingservice.web;

import com.emsi.billingservice.entities.Bill;
import com.emsi.billingservice.entities.Customer;
import com.emsi.billingservice.entities.Product;
import com.emsi.billingservice.entities.ProductItem;
import com.emsi.billingservice.feign.CustomerRestClient;
import com.emsi.billingservice.feign.ProductItemRestClient;
import com.emsi.billingservice.model.CustomerModel;
import com.emsi.billingservice.model.ProductModel;
import com.emsi.billingservice.repository.BillRepository;
import com.emsi.billingservice.repository.CustomerRepository;
import com.emsi.billingservice.repository.ProductItemRepository;
import com.emsi.billingservice.repository.ProductRepository;
import com.emsi.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class BillingRestController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/bills")
    public Bill addBill(@RequestBody Bill bill) {
        return billingService.addBill(bill);
    }

    @GetMapping
    public Collection<Bill> getAllbills() {
        return billingService.getBills();
    }

    @GetMapping(path = "/bills/{id}")
    public Bill getBill(@PathVariable Long id) {
        return billingService.getBillById(id);
    }
}
