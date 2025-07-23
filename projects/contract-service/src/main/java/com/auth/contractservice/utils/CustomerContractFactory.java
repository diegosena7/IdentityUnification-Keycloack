package com.auth.contractservice.utils;

import com.auth.contractservice.model.ClientRequestDTO;
import com.auth.contractservice.model.CustomerContractEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerContractFactory {

    public static CustomerContractEntity generatedContract(ClientRequestDTO clientRequestDTO) {
        return CustomerContractEntity.builder()
                .customerId(/*"cus-" +  */clientRequestDTO.getCustomerId())
                .cpf(clientRequestDTO.getCustomerId())
                .name(CustomerContractEntity.Name.builder()
                        .given(clientRequestDTO.getFirstName())
                        .family(clientRequestDTO.getLastName())
                        .build())
                .contacts(CustomerContractEntity.Contacts.builder()
                        .primaryEmail(clientRequestDTO.getEmail())
                        .mobile("55" + (ThreadLocalRandom.current().nextInt(1190000000, 1199999999)))
                        .build())
                .primaryResidence(randomAddress(true))
                .mailing(randomAddress(false))
                .preferences(CustomerContractEntity.Preferences.builder()
                        .marketingOptIn(ThreadLocalRandom.current().nextBoolean())
                        .informationOptIn(ThreadLocalRandom.current().nextBoolean())
                        .preferredChannel("mobile")
                        .build())
                .memberships(List.of(
                        CustomerContractEntity.Membership.builder()
                                .productCode("PROD-" + UUID.randomUUID())
                                .productName("Produto Teste")
                                .status("active")
                                .contractId("CT-" + UUID.randomUUID())
                                .benefitTier("Gold")
                                .build()
                ))
                .context(CustomerContractEntity.Context.builder()
                        .lastLoginChannel("web")
                        .lastLoginAt(OffsetDateTime.now())
                        .lastKnownLocation("-23.5,-46.6")
                        .build())
                .build();
    }

    private static CustomerContractEntity.Address randomAddress(boolean primary) {
        return CustomerContractEntity.Address.builder()
                .type(primary ? "residential" : "correspondence")
                .isPrimary(primary)
                .street("Rua " + randomString("Teste"))
                .number(String.valueOf(ThreadLocalRandom.current().nextInt(1, 999)))
                .complement("Comp " + randomString("X"))
                .neighborhood("Bairro " + randomString("Y"))
                .city("Cidade " + randomString("Z"))
                .state("SP")
                .postalCode("01000-000")
                .country("BR")
                .verifiedAt(OffsetDateTime.now())
                .build();
    }

    private static String randomString(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    /*private static String randomCpf() {
        // gera algo dummy com 11 dígitos
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(0, 9));
        }
        return sb.toString();
    }*/
}

