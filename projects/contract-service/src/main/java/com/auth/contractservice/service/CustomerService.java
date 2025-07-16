package com.auth.contractservice.service;

import org.springframework.stereotype.Service;

import com.auth.contractservice.model.CustomerContractEntity;
import com.auth.contractservice.repository.ContratoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ContratoRepository contratoRepository;
    public CustomerContractEntity getContractByContractId(String customerId) {
        return contratoRepository.findByCustomerId(customerId).get(0);
    }
}
