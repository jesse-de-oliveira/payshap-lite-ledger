package com.example.demo.service;


import com.example.demo.entity.Account;
import com.example.demo.entity.NaturalKey;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.NaturalKeyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    //declaration of dependencies
    private final AccountRepository accountRepository;
    private final NaturalKeyRepository naturalKeyRepository;

    //dependency injection via constructor
    public AccountService (AccountRepository accountRepository, NaturalKeyRepository naturalKeyRepository) {
        this.accountRepository = accountRepository;
        this.naturalKeyRepository = naturalKeyRepository;
    }

    @Transactional
    public Account registerAccount(String ownerName, String aliasValue, String keyType) {

        Account newAccount = new Account(ownerName);
        Account savedAccount = accountRepository.save(newAccount);

        NaturalKey naturalKey = new NaturalKey(aliasValue, keyType, savedAccount);
        naturalKeyRepository.save(naturalKey);

        return savedAccount;
    }
}
