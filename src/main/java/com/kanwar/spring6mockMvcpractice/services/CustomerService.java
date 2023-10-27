package com.kanwar.spring6mockMvcpractice.services;

import com.kanwar.spring6mockMvcpractice.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(UUID id);

    Customer createNewCustomer(Customer customer);

    Customer updateCustomer(UUID id, Customer customer);

}
