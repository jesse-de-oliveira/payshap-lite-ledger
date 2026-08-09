package com.example.demo.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "natural_keys")
public class NaturalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; //pk

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; //fk

    @Column(unique = true, nullable = false)
    private String aliasValue;

    @Column(nullable = false)
    private String keyType;

    @Column(nullable = false)
    private boolean isActive;

    //No-arg constructor
    protected NaturalKey() {
    }

    //parameterized constructor
    public NaturalKey(String aliasValue, String keyType, Account account) {
        this.aliasValue = aliasValue;
        this.keyType = keyType;
        this.account = account;
        this.isActive = true;
    }

    // Getters & Setters (No setters! Once the NaturalKey is created via the constructor, it cannot be tampered with.)
    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getAliasValue() {
        return aliasValue;
    }

    public String getKeyType() {
        return keyType;
    }

    public void deactivate() {
        this.isActive = false;
    }


}
