package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BalanceResponse(
        UUID accountId,
        BigDecimal currentBalance
) {}
