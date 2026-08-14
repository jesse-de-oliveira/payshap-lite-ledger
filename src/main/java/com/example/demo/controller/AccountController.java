package com.example.demo.controller;

import com.example.demo.dto.RegisterAccountRequest;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin("*")
public class AccountController {

    //declaration
    private final AccountService accountService;

    //constructor
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //methods
    @PostMapping
    public ResponseEntity<Account> registerAccount(@RequestBody RegisterAccountRequest request) {
        Account newAccount = accountService.registerAccount(request.ownerName(), request.aliasValue(), request.keyType());

        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

}
