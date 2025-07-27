package com.auth.contractservice.controller.dto;

import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO(
    UUID id,
    String givenName,
    String familyName,
    String document,
    String email,
    Boolean emailVerified,
    List<ContactDTO> contacts,
    List<AddressDTO> addresses,
    PreferencesDTO preferences,
    List<MembershipDTO> memberships
) {}