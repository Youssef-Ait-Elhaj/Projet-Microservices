package com.emsi.billingservice.service;

import com.emsi.billingservice.entities.Bill;
import com.emsi.billingservice.entities.Customer;
import com.emsi.billingservice.entities.Product;
import com.emsi.billingservice.entities.ProductItem;

import java.util.Collection;
import java.util.function.Consumer;

public interface IBillingService {
    Bill addBill(Bill bill);
    Consumer<Product> productConsumer();
    Consumer<Customer> customerConsumer();
    ProductItem getProductItemById(Long productItemId);
    Bill getBillById(Long billId);
    Collection<Bill> getBills();
}
