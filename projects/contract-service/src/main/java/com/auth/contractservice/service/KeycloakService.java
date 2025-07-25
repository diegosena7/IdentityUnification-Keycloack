package com.auth.contractservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth.contractservice.model.ClientRequestDTO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class KeycloakService {

    private final WebClient webClient;
    private KeycloakServiceProperties clientProperties;

    public KeycloakService(KeycloakServiceProperties clientProperties
    ) {
        this.clientProperties = clientProperties;
        this.webClient = WebClient.builder().baseUrl(clientProperties.getUrl()).build();
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
                .uri("/admin/realms/" + clientProperties.getRealm() + "/users")
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
                        .path("/admin/realms/" + clientProperties.getRealm() + "/users")
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
        form.add("client_id", clientProperties.getAdminClientId());
        form.add("username", clientProperties.getAdminUsername());
        form.add("password", clientProperties.getAdminPassword());

        var response = webClient.post()
        // TODO Verificar o pq do uso do realm master.
                .uri("/realms/master/protocol/openid-connect/token")
                .bodyValue(form)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (String) response.get("access_token");
    }
}
