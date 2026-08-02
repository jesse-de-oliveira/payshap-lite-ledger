package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; //pk
    private String ownerName;
    private BigDecimal balance;
    private boolean isActive;
    private LocalDateTime createdAt;

    //no-arg constructor for JPA/Hibernate
    protected Account() {
    }

    //parameterized constructor
    public Account(String ownerName) {
        this.ownerName = ownerName;
        this.balance = BigDecimal.ZERO;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    //id getter
    public UUID getId() {
        return id;
    }

    //created_at getter
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //ownerName getter & setter
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String newName) {
        this.ownerName = newName;
    }

    //balance getter & setter
    public BigDecimal getBalance() {
        return balance;
    }
    /*private void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }*/ //removed since exposing this method bypasses auditing trail (behavioral methods to be implemented later)

    //is_active getter & setter
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean newAccountActivityStatus) {
        this.isActive = newAccountActivityStatus;
    }


}
