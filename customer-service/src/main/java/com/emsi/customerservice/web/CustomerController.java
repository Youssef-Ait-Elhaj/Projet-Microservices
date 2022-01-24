package com.emsi.customerservice.web;

import com.emsi.customerservice.entities.Customer;
import com.emsi.customerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable("id") Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    @PostMapping("/customers")
    public Customer publish(@RequestBody Customer customer) {
        Customer customer1 = customerRepository.save(customer);
        streamBridge.send("CUSTOMER_TOPIC", customer1);
        return customer1;
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable("id") Long customerId, @RequestBody Customer newCustomer) {
        newCustomer.setId(customerId);
        return customerRepository.save(newCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable("id") Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
