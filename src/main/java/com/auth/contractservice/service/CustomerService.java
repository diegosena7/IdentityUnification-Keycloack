package com.auth.contractservice.service;

import com.auth.contractservice.model.CustomerContractEntity;
import com.auth.contractservice.repository.ContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ContratoRepository contratoRepository;
    public CustomerContractEntity getContractByContractId(String customerId) {
        return contratoRepository.findByCustomerId(customerId).get(0);
    }
}
