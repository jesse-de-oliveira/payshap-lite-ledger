package com.example.demo.service;


import com.example.demo.entity.Account;
import com.example.demo.entity.NaturalKey;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.NaturalKeyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    public void softDeleteAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        account.deactivate();

        accountRepository.save(account);
    }

    @Transactional
    public void updateOwnerName(UUID accountId,String newOwnerName) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        account.updateOwnerName(newOwnerName);

        accountRepository.save(account);
    }

    @Transactional
    public void addAliasToAccount(UUID accountId, String newAlias, String keyType) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        NaturalKey additionalAlias = new NaturalKey(newAlias, keyType, account);

        naturalKeyRepository.save(additionalAlias);
    }

    @Transactional
    public void removeAlias(String aliasToBeRemoved) {
        NaturalKey alias = naturalKeyRepository.findByAliasValue(aliasToBeRemoved).orElseThrow(() -> new RuntimeException("Alias not found"));

        alias.deactivate();

        naturalKeyRepository.save(alias);
    }
}
