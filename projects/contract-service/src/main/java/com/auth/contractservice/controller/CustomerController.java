package com.auth.contractservice.controller;

import com.auth.contractservice.model.old.CustomerContractEntity;
import com.auth.contractservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/contract/{customerId}")
    public ResponseEntity<CustomerContractEntity> getContractById(@PathVariable String customerId){
        CustomerContractEntity contractByContractId = customerService.getContractByContractId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(contractByContractId);
    }
}
