package com.auth.contractservice.service;

import com.auth.contractservice.model.ClientRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KeycloakService {

    private final WebClient webClient;
    private final String bootcampRealm;
    private final String adminClientId;
    private final String adminUsername;
    private final String adminPassword;

    public KeycloakService(
            @Value("${keycloak.url}") String keycloakUrl,
            @Value("${keycloak.realm}") String bootcampRealm,
            @Value("${keycloak.admin-client-id}") String adminClientId,
            @Value("${keycloak.admin-username}") String adminUsername,
            @Value("${keycloak.admin-password}") String adminPassword
    ) {
        this.webClient = WebClient.builder().baseUrl(keycloakUrl).build();
        this.bootcampRealm = bootcampRealm;
        this.adminClientId = adminClientId;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public String createUser(ClientRequestDTO dto) {
        log.info("Criando usuário: {}", dto.getUsername());

        String token = getAdminToken();

        Map<String, Object> body = Map.of(
                "username", dto.getUsername(),
                "email", dto.getEmail(),
                "enabled", true,
                "firstName", dto.getFirstName(),
                "lastName", dto.getLastName(),
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", dto.getPassword(),
                        "temporary", false
                ))
        );


        var response = webClient.post()
                .uri("/admin/realms/" + bootcampRealm + "/users")
                .header("Authorization", "Bearer " + token)
                .bodyValue(body)
                .exchangeToMono(res -> {
                    if (res.statusCode().is2xxSuccessful()) {
                        return res.toBodilessEntity();
                    } else if (res.statusCode().value() == 409) {
                        return res.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Usuário já existe: " + error)));
                    } else {
                        return res.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Erro ao criar usuário: " + error)));
                    }
                }).block();

        assert response != null;
        log.info("Resposta criação do usuário no keycloack: {}", response.getStatusCode());

        // Agora buscar o ID do usuário recém-criado
        var user = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/realms/" + bootcampRealm + "/users")
                        .queryParam("username", dto.getUsername())
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(Map.class)
                .blockFirst();

        return user != null ? (String) user.get("id") : null;
    }

    private String getAdminToken() {
        log.info("Buscando o token admin... ");
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", adminClientId);
        form.add("username", adminUsername);
        form.add("password", adminPassword);

        Map response = webClient.post()
                .uri("/realms/master/protocol/openid-connect/token")
                .bodyValue(form)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (String) response.get("access_token");
    }
}
