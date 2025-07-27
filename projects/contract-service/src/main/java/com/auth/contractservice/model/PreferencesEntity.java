package com.auth.contractservice.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferencesEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotNull
    private Boolean marketingOptIn;

    @Column(nullable = false)
    @NotNull
    private Boolean informationOptIn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @NotNull
    private PreferredChannel preferredChannel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private CustomerEntity customer;
}
