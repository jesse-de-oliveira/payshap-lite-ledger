package com.example.demo.dto;

    public record RegisterAccountRequest(
            String ownerName,
            String aliasValue,
            String keyType
    ) {}

