package com.auth.contractservice.controller.dto;

import com.auth.contractservice.model.AddressType;

public record AddressDTO(
    AddressType type,
    Boolean isPrimary,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String postalCode,
    String country,         // continua String para código ISO
    String verifiedAt       // ou use OffsetDateTime, se preferir
) {}
