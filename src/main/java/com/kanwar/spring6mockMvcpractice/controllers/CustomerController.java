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
    public ResponseEntity<List<Customer>> getAllCustomers(){
        log.info("--CustomerController::getAllCustomers--");

        return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerService.getAllCustomers());
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") UUID id){
        log.info("--CustomerController::getCustomerById--");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerById(id));
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer){log.info("--CustomerController::createNewCustomer--");

        Customer createdCustomer =  customerService.createNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", createdCustomer.getId().toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(createdCustomer);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer){
        log.info("--CustomerController::updateCustomer--");

        Customer updatedCustomer = customerService.updateCustomer(id, customer);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(updatedCustomer);
    }
}
