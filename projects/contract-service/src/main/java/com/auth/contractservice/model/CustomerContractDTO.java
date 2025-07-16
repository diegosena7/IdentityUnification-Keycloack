package com.auth.contractservice.model;

import lombok.Data;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class CustomerContractDTO {
    private String customerId;
    private Name name;
    private String cpf;
    private Contacts contacts;
    private Addresses addresses;
    private Preferences preferences;
    private List<Membership> memberships;
    private Context context;

    @Data
    @Builder
    public static class Name {
        private String given;
        private String family;
    }

    @Data
    @Builder
    public static class Contacts {
        private String primaryEmail;
        private String mobile;
    }

    @Data
    @Builder
    public static class Addresses {
        private Address primaryResidence;
        private Address mailing;
    }

    @Data
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
        private OffsetDateTime verifiedAt; // para campos de data
    }

    @Data
    @Builder
    public static class Preferences {
        private boolean marketingOptIn;
        private boolean informationOptIn;
        private String preferredChannel;
    }

    @Data
    @Builder
    public static class Membership {
        private String productCode;
        private String productName;
        private String status;
        private String contractId;
        private String benefitTier;
        private String quoteId;
    }

    @Data
    @Builder
    public static class Context {
        private String lastLoginChannel;
        private OffsetDateTime lastLoginAt;
        private String lastKnownLocation;
    }
}

