package com.kanwar.spring6mockMvcpractice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanwar.spring6mockMvcpractice.domain.Customer;
import com.kanwar.spring6mockMvcpractice.services.CustomerService;
import com.kanwar.spring6mockMvcpractice.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Autowired
    ObjectMapper objectMapper;

    // creating this obj just to use the same test data crated in constructor of CustomerServiceImpl class.
    CustomerServiceImpl customerServiceimp = new CustomerServiceImpl();
    List<Customer> allCustomers = customerServiceimp.getAllCustomers();
    Customer testCustomer = allCustomers.get(0);

    @Test
    void getAllCustomers() throws Exception {

        given(customerService.getAllCustomers()).willReturn(customerServiceimp.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void getCustomerById() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(testCustomer);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name",is(testCustomer.getName())));

        verify(customerService).getCustomerById(uuidCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(testCustomer.getId());

    }

    @Test
    void createNewCustomer() throws Exception {
        testCustomer.setName("newCustomer");
        Customer created = allCustomers.get(2);
        given(customerService.createNewCustomer(testCustomer)).willReturn(created);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .content(objectMapper.writeValueAsString(testCustomer))
                .contentType(MediaType.APPLICATION_JSON));

        verify(customerService).createNewCustomer(customerCaptor.capture());

        assertThat(customerCaptor.getValue().getName()).isEqualTo(testCustomer.getName());
    }

    @Test
    void updateCustomer() throws Exception {

        given(customerService.updateCustomer(testCustomer.getId(), testCustomer)).willReturn(testCustomer);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID,testCustomer.getId())
                .content(objectMapper.writeValueAsString(testCustomer))
                .contentType(MediaType.APPLICATION_JSON));

        verify(customerService).updateCustomer(uuidCaptor.capture(),customerCaptor.capture());

        assertThat(customerCaptor.getValue().getName()).isEqualTo(testCustomer.getName());
    }
}