package com.example.demo.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, updatable = false)
    private String idempotencyKey;
    private String status; // complete/pending/failed
    private LocalDateTime createdAt;
    @Column(updatable = false)
    private BigDecimal amount;

    //no-arg constructor
    protected Transaction() {
    }

    //parameterized constructor
    public Transaction(String idempotencyKey, BigDecimal amount) {
        this.idempotencyKey = idempotencyKey;
        this.amount = amount;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    //getters & setters
    public UUID getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //Status: behavioral methods
    public void markAsCompleted() {
        this.status = "COMPLETED";
    }

    public void markAsFailed() {
        this.status = "FAILED";
    }
}
