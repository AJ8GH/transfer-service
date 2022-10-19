package io.github.aj8gh.transfer.api.service;

import io.github.aj8gh.transfer.api.domain.Account;
import io.github.aj8gh.transfer.api.domain.CreateAccountRequest;
import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.respository.AccountRepository;

public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void transfer(final TransferRequest request) {
    debit(request.getSourceAccountId(), request.getAmount());
    credit(request.getTargetAccountId(), request.getAmount());
  }

  public Account create(CreateAccountRequest request) {
    var account = Account.builder()
        .balance(request.getInitialBalance())
        .currency(request.getCurrency())
        .build();

    accountRepository.save(account);
    return account;
  }

  private void debit(String accountId, long amount) {
    var account = accountRepository.findById(accountId).get();
    account.debit(amount);
    accountRepository.save(account);
  }

  private void credit(String accountId, long amount) {
    var account = accountRepository.findById(accountId).get();
    account.credit(amount);
    accountRepository.save(account);
  }

  public Iterable<Account> findAll() {
    return accountRepository.findAll();
  }
}
