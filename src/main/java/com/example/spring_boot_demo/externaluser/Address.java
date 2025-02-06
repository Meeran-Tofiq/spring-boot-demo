package com.example.spring_boot_demo.externaluser;

/**
 * Address
 */
public record Address(
        String street,
        String suite,
        String city,
        String zipCode,
        Geo geo) {
}
