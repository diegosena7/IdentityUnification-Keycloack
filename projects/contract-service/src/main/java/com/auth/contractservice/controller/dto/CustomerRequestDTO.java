package com.auth.contractservice.controller.dto;

import java.util.List;

public record CustomerRequestDTO(
    String givenName,
    String familyName,
    String document,
    String email,
    Boolean emailVerified,
    CredentialsDTO credentials,
    List<ContactDTO> contacts,
    List<AddressDTO> addresses,
    PreferencesDTO preferences,
    List<MembershipDTO> memberships
) {}
