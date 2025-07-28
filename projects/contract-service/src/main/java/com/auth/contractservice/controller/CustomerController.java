package com.auth.contractservice.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.contractservice.model.CustomerEntity;
import com.auth.contractservice.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/customer/{customerId}")
    public ResponseEntity<CustomerEntity> getContractById(@PathVariable String customerId){
        try {
            var customerEntity = customerService.searchCustomerById(customerId);
            return ResponseEntity.ok(customerEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
