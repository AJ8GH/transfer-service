package io.github.aj8gh.transfer.api.service;

import io.github.aj8gh.transfer.api.domain.Transaction;
import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.respository.TransactionRepository;

public class TransferService {

  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  public TransferService(TransactionRepository transactionRepository,
                         AccountService accountService) {
    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
  }

  public Transaction transfer(final TransferRequest request) {
    accountService.transfer(request);
    var transaction = Transaction.builder()
        .sourceAccountId(request.getSourceAccountId())
        .targetAccountId(request.getTargetAccountId())
        .currency(request.getCurrency())
        .amount(request.getAmount())
        .build();

    transactionRepository.save(transaction);
    return transaction;
  }
}
