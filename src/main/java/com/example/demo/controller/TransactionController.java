package com.example.demo.controller;

import com.example.demo.dto.DepositRequest;
import com.example.demo.dto.TransferRequest;
import com.example.demo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin("*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferRequest(@RequestBody TransferRequest request) {
        transactionService.executeTransfer(request.fromAccount(), request.toAccount(), request.amount(), request.idempotencyKey());

        return ResponseEntity.ok("Transaction executed successfully");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositRequest(@RequestBody DepositRequest request) {
        transactionService.executeDeposit(request.accountId(), request.amount(), request.idempotencyKey());

        return ResponseEntity.ok("Deposit executed successfully");
    }

}
