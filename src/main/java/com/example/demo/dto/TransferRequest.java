package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        UUID fromAccount,
        UUID toAccount,
        BigDecimal amount,
        String idempotencyKey
) {}
    