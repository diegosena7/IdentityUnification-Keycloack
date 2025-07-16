package com.auth.contractservice.repository;

import com.auth.contractservice.model.CustomerContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<CustomerContractEntity, Long> {
    List<CustomerContractEntity> findByCustomerId(String customerId);
}