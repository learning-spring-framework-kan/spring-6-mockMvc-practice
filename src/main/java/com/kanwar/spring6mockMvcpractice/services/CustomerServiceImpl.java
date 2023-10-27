package com.kanwar.spring6mockMvcpractice.services;

import com.kanwar.spring6mockMvcpractice.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID,Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("customer1")
                .dob(LocalDateTime.of(1992,01,01,1,11))
                .ssnLast4(1111)
                .establishedTimestamp(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("customer2")
                .dob(LocalDateTime.of(1992,02,02,2,22))
                .ssnLast4(2222)
                .establishedTimestamp(LocalDateTime.now())
                .build();
        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .name("customer3")
                .dob(LocalDateTime.of(1992,03,03,3,33))
                .ssnLast4(3333)
                .establishedTimestamp(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(),customer1);
        customerMap.put(customer2.getId(),customer2);
        customerMap.put(customer3.getId(),customer3);
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("--CustomerServiceImpl::getAllCustomers--");
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.info("--CustomerServiceImpl::getCustomerById--");
        return customerMap.get(id);
    }

    @Override
    public Customer createNewCustomer(Customer customer) {
        log.info("--CustomerServiceImpl::createNewCustomer--");
        customer.setId(UUID.randomUUID());
        this.customerMap.put(customer.getId(),customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        log.info("--CustomerServiceImpl::updateCustomer--");
        if(customerMap.containsKey(id)){
            Customer customer1 = customerMap.get(id);
            customer1.setEstablishedTimestamp(LocalDateTime.now());
            customer1.setName(customer.getName());
            customer1.setDob(customer.getDob());
            customer1.setSsnLast4(customer.getSsnLast4());
            customerMap.put(id,customer1);
            return customer1;
        }
        log.info("--CustomerServiceImpl::updateCustomer-:: customer does not exist");
        return null;
    }
}
