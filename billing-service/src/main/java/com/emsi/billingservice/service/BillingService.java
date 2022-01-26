package com.emsi.billingservice.service;

import com.emsi.billingservice.entities.Bill;
import com.emsi.billingservice.entities.Customer;
import com.emsi.billingservice.entities.ProductItem;
import com.emsi.billingservice.entities.Product;
import com.emsi.billingservice.model.CustomerModel;
import com.emsi.billingservice.model.ProductModel;
import com.emsi.billingservice.repository.BillRepository;
import com.emsi.billingservice.repository.CustomerRepository;
import com.emsi.billingservice.repository.ProductItemRepository;
import com.emsi.billingservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

@Service
public class BillingService implements IBillingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private BillRepository billRepository;

    public Collection<Bill> getAllBills() {
        return billRepository.findAll();
    }
    public Bill addBill(Bill bill) {

        Bill bill1 = billRepository.save(bill);

        Collection<ProductItem> productItems = bill1.getProductItems();
        productItems.forEach(productItem -> {
            productItem.setBill(bill1);
            productItemRepository.save(productItem);
        });

        return bill1;
    }

    @Bean
    public Consumer<Product> productConsumer() {
        return (input) -> {
            ProductModel productModel = new ProductModel(null, input.getName(), input.getPrice(), input.getQuantity());

            Product savedProduct = productRepository.save(input);
            ProductItem productItem = new ProductItem(null, input.getQuantity(), input.getPrice(),
                    savedProduct.getId(), null, productModel, input.getName());
            productItemRepository.save(productItem);
        };
    }

    @Bean
    public Consumer<Customer> customerConsumer() {
        return (input) -> {
            customerRepository.save(input);
        };
    }

    public Bill getBillById(Long billId) {
        Bill bill = billRepository.findById(billId).get();
        Customer customer = customerRepository.findById(bill.getCustomerID()).get();
        CustomerModel customerModel = new CustomerModel(customer.getId(), customer.getName(), customer.getEmail());
        bill.setCustomerModel(customerModel);
        Collection<ProductItem> productItemCollection = new ArrayList<>();
        bill.getProductItems().forEach(pi -> {

            Product product = productRepository.findById(pi.getProductID()).get();
            ProductModel productModel = new ProductModel(product.getId(), product.getName(), product.getPrice(),
                    product.getQuantity());
            pi.setProductModel(productModel);
            pi.setProductName(product.getName());
            productItemCollection.add(pi);
        });

        bill.setProductItems(productItemCollection);
        return bill;
    }

    @Override
    public Collection<Bill> getBills() {
        return billRepository.findAll();
    }

    @Override
    public ProductItem getProductItemById(Long productItemId) {
        return productItemRepository.findById(productItemId).orElse(null);
    }
}
