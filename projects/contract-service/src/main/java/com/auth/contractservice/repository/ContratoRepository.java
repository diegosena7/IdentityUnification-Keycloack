package com.auth.contractservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.contractservice.model.old.CustomerContractEntity;

@Repository
public interface ContratoRepository extends JpaRepository<CustomerContractEntity, Long> {
    List<CustomerContractEntity> findByCustomerId(String customerId);
}