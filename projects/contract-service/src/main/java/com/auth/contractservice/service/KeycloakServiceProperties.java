package com.auth.contractservice.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "keycloak")
@Configuration
@Data
public class KeycloakServiceProperties {

    private String url;
    private String realm;
    private String adminClientId;
    private String adminUsername;
    private String adminPassword;

}
