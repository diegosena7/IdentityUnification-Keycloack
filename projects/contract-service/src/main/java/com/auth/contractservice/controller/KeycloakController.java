package com.auth.contractservice.controller;

import com.auth.contractservice.model.ClientRequestDTO;
import com.auth.contractservice.model.CustomerContractEntity;
import com.auth.contractservice.model.UserProfileEntity;
import com.auth.contractservice.repository.ContratoRepository;
import com.auth.contractservice.repository.UserProfileRepository;
import com.auth.contractservice.service.KeycloakService;
import com.auth.contractservice.utils.CustomerContractFactory;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KeycloakController {

    private final UserProfileRepository userProfileRepository;
    private final ContratoRepository contratoRepository;
    private final KeycloakService keycloakService;


    @PostMapping(value = "/register")
    public ResponseEntity<String> registerClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO){
        String keycloackId = keycloakService.createUser(clientRequestDTO);

        UserProfileEntity userProfileEntity = new UserProfileEntity();
                userProfileEntity.setKeycloakUserId(keycloackId);
                userProfileEntity.setCpf(clientRequestDTO.getCustomerId());

        CustomerContractEntity contrato = CustomerContractFactory.generatedContract(clientRequestDTO);
        contrato.setKeycloakUserId(keycloackId);

        contratoRepository.save(contrato);
        userProfileRepository.save(userProfileEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso.");
    }
}
