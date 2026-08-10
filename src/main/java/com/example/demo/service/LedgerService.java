package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.LedgerEntry;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LedgerEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class LedgerService {

    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountRepository accountRepository;

    public LedgerService(LedgerEntryRepository ledgerEntryRepository, AccountRepository accountRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateCurrentBalance(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal totalCredits = ledgerEntryRepository.sumAmountByAccountIdAndType(accountId, "CREDIT");

        BigDecimal totalDebits = ledgerEntryRepository.sumAmountByAccountIdAndType(accountId, "DEBIT");

        return totalCredits.subtract(totalDebits);
    }
}
