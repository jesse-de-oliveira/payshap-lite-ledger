package com.example.demo.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account; //fk

    @ManyToOne
    @JoinColumn(name = "transaction_id", updatable = false)
    private Transaction transaction; //fk

    @Column(updatable = false)
    private BigDecimal amount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private String entryType; //CREDIT/DEBIT

    //no-arg constructor
    protected LedgerEntry() {
    }

    //parameterized constructor
    public LedgerEntry(Account account, Transaction transaction, BigDecimal amount, String entryType) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.entryType = entryType;
    }

    //getters & setter
    public UUID getID() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEntryType() {
        return entryType;
    }
}
