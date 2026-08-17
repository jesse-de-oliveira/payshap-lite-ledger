package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequest (
    UUID accountId,
    BigDecimal amount,
    String idempotencyKey
) {}
