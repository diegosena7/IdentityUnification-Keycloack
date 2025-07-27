package com.auth.contractservice.controller.dto;

import com.auth.contractservice.model.ContactType;

public record ContactDTO(
    ContactType type,
    Boolean isPrimary,
    String label,
    String value
) {}
