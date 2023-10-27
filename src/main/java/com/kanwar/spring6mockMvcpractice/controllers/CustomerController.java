package com.kanwar.spring6mockMvcpractice.controllers;

import com.kanwar.spring6mockMvcpractice.domain.Customer;
import com.kanwar.spring6mockMvcpractice.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public static final String CUSTOMER_PATH = "/api/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers(){
        log.info("--CustomerController::getAllCustomers--");
       return customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("id") UUID id){
        log.info("--CustomerController::getCustomerById--");
        return customerService.getCustomerById(id);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity createNewCustomer(@RequestBody Customer customer){
        log.info("--CustomerController::createNewCustomer--");
        Customer createdCustomer =  customerService.createNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", createdCustomer.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer){
        log.info("--CustomerController::updateCustomer--");
        Customer customer1 = customerService.updateCustomer(id, customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("updatedCustomer",customer1.toString());
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }
}
