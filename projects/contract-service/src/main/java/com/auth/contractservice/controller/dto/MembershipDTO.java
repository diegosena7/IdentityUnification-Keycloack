package com.auth.contractservice.controller.dto;

import com.auth.contractservice.model.MembershipStatus;

public record MembershipDTO(
    String productCode,
    String productName,
    MembershipStatus status,
    String contractId,
    String benefitTier,
    String quoteId
) {}
