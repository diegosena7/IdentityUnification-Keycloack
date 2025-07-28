package com.auth.contractservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.contractservice.controller.dto.AddressDTO;
import com.auth.contractservice.controller.dto.ContactDTO;
import com.auth.contractservice.controller.dto.CustomerRequestDTO;
import com.auth.contractservice.controller.dto.MembershipDTO;
import com.auth.contractservice.model.AddressEntity;
import com.auth.contractservice.model.ContactEntity;
import com.auth.contractservice.model.CustomerEntity;
import com.auth.contractservice.model.MembershipEntity;
import com.auth.contractservice.model.PreferencesEntity;
import com.auth.contractservice.model.directory.DirectoryUserCredentials;
import com.auth.contractservice.model.directory.DirectoryUserProfile;
import com.auth.contractservice.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {


    private final KeycloakService keycloakService;
    private final CustomerRepository customerRepository;

    public Optional<CustomerEntity> searchCustomerById(String customerID) {
        return customerRepository.findById(UUID.fromString(customerID));
    }

    public Optional<CustomerEntity> searchCustomerByEmail(String customerEmail) {
        return customerRepository.findByEmail(customerEmail);
    }    

    public CustomerEntity createUser(CustomerRequestDTO request) {
        // Cria usuário no IDP Provider.
        String customerID = keycloakService.createUser(toDirectoryUserProfile(request));

        // Converte ID do IDP Provider em UUID.
        var uuid = UUID.fromString(customerID);
        
        // Converte dados da request para entidade a ser persistida.
        var customerEntity = toCustomerEntity(request, uuid);

        // Persiste a entidade.
        var savedCustomer = customerRepository.save(customerEntity);

        return savedCustomer;
    }

    private CustomerEntity toCustomerEntity(CustomerRequestDTO request, UUID uuid) {

        var customerEntity = new CustomerEntity();
        customerEntity.setId(uuid);
        customerEntity.setGivenName(request.givenName());
        customerEntity.setFamilyName(request.familyName());
        customerEntity.setEmail(request.email());
        customerEntity.setEmailVerified(request.emailVerified());
        customerEntity.setDocument(request.document());
        customerEntity.setPicture(request.pictureUrl());

        if (request.contacts() != null && !request.contacts().isEmpty()) {
            List<ContactEntity> contacts = new ArrayList<>();
            for (ContactDTO contactRequest : request.contacts()) {
                var contactEntity = new ContactEntity();
                contactEntity.setCustomer(customerEntity);
                contactEntity.setIsPrimary(contactRequest.isPrimary());
                contactEntity.setType(contactRequest.type());
                contactEntity.setLabel(contactRequest.label());
                contactEntity.setValue(contactRequest.value());
                contacts.add(contactEntity);
            }
            customerEntity.setContacts(contacts);
        }

        if (request.addresses() != null && !request.addresses().isEmpty()) {
            List<AddressEntity> addresses = new ArrayList<>();
            for (AddressDTO addressRequest : request.addresses()) {
                var addressEntity = new AddressEntity();
                addressEntity.setCustomer(customerEntity);
                addressEntity.setCity(addressRequest.city());
                addressEntity.setComplement(addressRequest.complement());
                addressEntity.setCountry(addressRequest.country());
                addressEntity.setIsPrimary(addressRequest.isPrimary());
                addressEntity.setNeighborhood(addressRequest.neighborhood());
                addressEntity.setNumber(addressRequest.number());
                addressEntity.setPostalCode(addressRequest.postalCode());
                addressEntity.setState(addressRequest.state());
                addressEntity.setStreet(addressRequest.street());
                addressEntity.setType(addressRequest.type());
                addresses.add(addressEntity);
            }

            customerEntity.setAddresses(addresses);
        }

        if (request.preferences() != null) {
            var preferencesEntity = new PreferencesEntity();
            preferencesEntity.setCustomer(customerEntity);
            preferencesEntity.setInformationOptIn(request.preferences().informationOptIn());
            preferencesEntity.setMarketingOptIn(request.preferences().marketingOptIn());
            preferencesEntity.setPreferredChannel(request.preferences().preferredChannel());
            customerEntity.setPreferences(preferencesEntity);
        }

        if (request.memberships() != null && !request.memberships().isEmpty()) {
            List<MembershipEntity> memberships = new ArrayList<>();
            for (MembershipDTO membershipRequest : request.memberships()) {
                var membershipEntity = new MembershipEntity();
                membershipEntity.setCustomer(customerEntity);
                membershipEntity.setBenefitTier(membershipRequest.benefitTier());
                membershipEntity.setContractId(membershipRequest.contractId());
                membershipEntity.setProductCode(membershipRequest.productCode());
                membershipEntity.setProductName(membershipRequest.productName());
                membershipEntity.setQuoteId(membershipRequest.quoteId());
                membershipEntity.setStatus(membershipRequest.status());
                memberships.add(membershipEntity);
            }
            customerEntity.setMemberships(memberships);
        }

        return customerEntity;
    }

    private DirectoryUserProfile toDirectoryUserProfile(CustomerRequestDTO request) {
        var userProfile = DirectoryUserProfile.builder()
            .email(request.email())
            .firstName(request.givenName())
            .lastName(request.familyName())
            .credentials(DirectoryUserCredentials.builder()
                .username(request.credentials().username())
                .password(request.credentials().password())
            .build())
            .picture(request.pictureUrl())
        .build();

        return userProfile;
    }
}
