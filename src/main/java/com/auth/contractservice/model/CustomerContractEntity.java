package com.auth.contractservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "customer_contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keycloakUserId;

    private String customerId;

    private String cpf;

    @Embedded
    private Name name;

    @Embedded
    private Contacts contacts;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "res_type")),
            @AttributeOverride(name = "isPrimary", column = @Column(name = "res_is_primary")),
            @AttributeOverride(name = "street", column = @Column(name = "res_street")),
            @AttributeOverride(name = "number", column = @Column(name = "res_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "res_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "res_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "res_city")),
            @AttributeOverride(name = "state", column = @Column(name = "res_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "res_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "res_country")),
            @AttributeOverride(name = "verifiedAt", column = @Column(name = "res_verified_at"))
    })
    private Address primaryResidence;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "mail_type")),
            @AttributeOverride(name = "isPrimary", column = @Column(name = "mail_is_primary")),
            @AttributeOverride(name = "street", column = @Column(name = "mail_street")),
            @AttributeOverride(name = "number", column = @Column(name = "mail_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "mail_complement")),
            @AttributeOverride(name = "neighborhood", column = @Column(name = "mail_neighborhood")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "state", column = @Column(name = "mail_state")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country")),
            @AttributeOverride(name = "verifiedAt", column = @Column(name = "mail_verified_at"))
    })
    private Address mailing;

    @Embedded
    private Preferences preferences;

    @ElementCollection
    @CollectionTable(name = "customer_membership", joinColumns = @JoinColumn(name = "customer_id"))
    private List<Membership> memberships;

    @Embedded
    private Context context;

    /* Embeddables */
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Name {
        private String given;
        private String family;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contacts {
        private String primaryEmail;
        private String mobile;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String type;
        private boolean isPrimary;
        private String street;
        private String number;
        private String complement;
        private String neighborhood;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private OffsetDateTime verifiedAt;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Preferences {
        private boolean marketingOptIn;
        private boolean informationOptIn;
        private String preferredChannel;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Membership {
        private String productCode;
        private String productName;
        private String status;
        private String contractId;
        private String benefitTier;
        private String quoteId;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Context {
        private String lastLoginChannel;
        private OffsetDateTime lastLoginAt;
        private String lastKnownLocation;
    }
}
