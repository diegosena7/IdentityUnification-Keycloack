package com.auth.contractservice.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth.contractservice.controller.dto.CustomerRequestDTO;
import com.auth.contractservice.model.CustomerEntity;
import com.auth.contractservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KeycloakController {

    private final CustomerService customerService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid CustomerRequestDTO customerRequest) {

        CustomerEntity createdUser = customerService.createUser(customerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/contract/{customerId}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body("Customer Created Successfully");
    }
}
