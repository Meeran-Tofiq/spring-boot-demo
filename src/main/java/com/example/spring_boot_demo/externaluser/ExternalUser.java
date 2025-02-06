package com.example.spring_boot_demo.externaluser;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;

public record ExternalUser(
        @Id @NotNull Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company) {
}
