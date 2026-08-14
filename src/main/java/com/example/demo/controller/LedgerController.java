package com.example.demo.controller;


import com.example.demo.dto.BalanceResponse;
import com.example.demo.service.LedgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ledger")
@CrossOrigin("*")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {

        BigDecimal balance = ledgerService.calculateCurrentBalance(accountId);

        BalanceResponse balanceResponse = new BalanceResponse(accountId, balance);

        return ResponseEntity.ok(balanceResponse);
    }
}
