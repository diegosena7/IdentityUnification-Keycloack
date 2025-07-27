package com.auth.contractservice.controller.dto;

import com.auth.contractservice.model.PreferredChannel;

public record PreferencesDTO(
    Boolean marketingOptIn,
    Boolean informationOptIn,
    PreferredChannel preferredChannel
) {}