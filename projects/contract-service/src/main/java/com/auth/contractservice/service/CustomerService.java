package com.auth.contractservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.contractservice.controller.dto.CustomerRequestDTO;
import com.auth.contractservice.model.CustomerEntity;
import com.auth.contractservice.model.directory.DirectoryUserCredentials;
import com.auth.contractservice.model.directory.DirectoryUserProfile;
import com.auth.contractservice.model.old.CustomerContractEntity;
import com.auth.contractservice.repository.ContratoRepository;
import com.auth.contractservice.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {


    private final KeycloakService keycloakService;
    private final CustomerRepository customerRepository;

    public CustomerEntity createUser(CustomerRequestDTO request) {
        String customerID = keycloakService.createUser(createDirectoryProfile(request));
        var uuid = UUID.fromString(customerID);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(uuid);
        customerEntity.setGivenName(request.givenName());
        customerEntity.setFamilyName(request.familyName());
        customerEntity.setEmail(request.email());
        customerEntity.setEmailVerified(request.emailVerified());
        customerEntity.setDocument(request.document());

        var savedCustomer = customerRepository.save(customerEntity);

        return savedCustomer;
    }

    private DirectoryUserProfile createDirectoryProfile(CustomerRequestDTO request) {
        var userProfile = DirectoryUserProfile.builder()
            .email(request.email())
            .firstName(request.givenName())
            .lastName(request.familyName())
            .credentials(DirectoryUserCredentials.builder()
                .username(request.credentials().username())
                .password(request.credentials().password())
            .build())
        .build();

        return userProfile;
    }

    private final ContratoRepository contratoRepository;
    public CustomerContractEntity getContractByContractId(String customerId) {
        return contratoRepository.findByCustomerId(customerId).get(0);
    }
}
