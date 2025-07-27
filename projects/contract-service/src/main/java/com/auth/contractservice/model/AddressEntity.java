package com.auth.contractservice.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AddressType type;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isPrimary = false;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String street;

    @Column(nullable = false, length = 20)
    @NotBlank
    private String number;

    @Column(length = 50)
    private String complement;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String neighborhood;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String city;

    @Column(nullable = false, length = 2)
    @NotBlank
    private String state;

    @Column(nullable = false, length = 12)
    @NotBlank
    private String postalCode;

    @Column(nullable = false, length = 2)
    @NotBlank
    private String country; // ISO 3166-1 alpha-2, ex: "BR"

    private OffsetDateTime verifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
