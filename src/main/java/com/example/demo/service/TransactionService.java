package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.LedgerEntry;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LedgerEntryRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final LedgerService ledgerService;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, LedgerEntryRepository ledgerEntryRepository, LedgerService ledgerService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.ledgerService = ledgerService;
    }

    @Transactional
    public void executeTransfer(UUID fromAccount, UUID toAccount, BigDecimal amount, String idempotencyKey) {
        if (transactionRepository.findByIdempotencyKey(idempotencyKey).isPresent()) {
            throw new RuntimeException("Transaction with this key already exists. Aborting now!");
        }

        Account senderAccount = accountRepository.findById(fromAccount).orElseThrow(() -> new RuntimeException("Sender Account does not exist!"));

        Account recipientAccount = accountRepository.findById(toAccount).orElseThrow(() -> new RuntimeException("Recipient Account does not exist!"));

        boolean balanceCheckSuccessful = ledgerService.calculateCurrentBalance(fromAccount).compareTo(amount) >= 0;
        if (!balanceCheckSuccessful) {
           throw new RuntimeException("Insufficient funds");
        }

        Transaction newTransaction = new Transaction(idempotencyKey, amount);
        transactionRepository.save(newTransaction);

        LedgerEntry senderLedgerEntry = new LedgerEntry(senderAccount, newTransaction, amount, "DEBIT");
        LedgerEntry recipientLedgerEntry = new LedgerEntry(recipientAccount, newTransaction, amount, "CREDIT");

        ledgerEntryRepository.save(senderLedgerEntry);
        ledgerEntryRepository.save(recipientLedgerEntry);

    }

    // TODO: Implement executeDeposit(UUID accountId, BigDecimal amount, String idempotencyKey)
    // Goal: Wrap a single-entry deposit in a standard Transaction for tracking.

    // TODO: Implement executeWithdrawal(UUID accountId, BigDecimal amount, String idempotencyKey)
    // Goal: Wrap a single-entry withdrawal in a standard Transaction, including balance checks.

}
