package com.auth.contractservice.controller;

import java.net.URI;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth.contractservice.controller.dto.CustomerRequestDTO;
import com.auth.contractservice.controller.dto.CustomerResponseDTO;
import com.auth.contractservice.model.CustomerEntity;
import com.auth.contractservice.model.dto.ClientRequestDTO;
import com.auth.contractservice.model.old.CustomerContractEntity;
import com.auth.contractservice.model.old.UserProfileEntity;
import com.auth.contractservice.repository.ContratoRepository;
import com.auth.contractservice.repository.CustomerRepository;
import com.auth.contractservice.repository.UserProfileRepository;
import com.auth.contractservice.service.CustomerService;
import com.auth.contractservice.service.KeycloakService;
import com.auth.contractservice.utils.CustomerContractFactory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KeycloakController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;
    private final UserProfileRepository userProfileRepository;
    private final ContratoRepository contratoRepository;
    private final KeycloakService keycloakService;

    //@PostMapping(value = "/register")
    public ResponseEntity<String> registerClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO){

        String customerID = ""; //keycloakService.createUser(clientRequestDTO);

        UserProfileEntity userProfileEntity = new UserProfileEntity();
                userProfileEntity.setKeycloakUserId(customerID);
                userProfileEntity.setCpf(customerID);

        CustomerContractEntity contrato = CustomerContractFactory.generatedContract(clientRequestDTO, customerID);
        contrato.setKeycloakUserId(customerID);

        contratoRepository.save(contrato);
        userProfileRepository.save(userProfileEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
    }

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
