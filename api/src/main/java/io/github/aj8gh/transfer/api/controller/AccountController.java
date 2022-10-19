package io.github.aj8gh.transfer.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.github.aj8gh.transfer.api.domain.AccountResponse;
import io.github.aj8gh.transfer.api.domain.CreateAccountRequest;
import io.github.aj8gh.transfer.api.mapper.AccountMapper;
import io.github.aj8gh.transfer.api.service.AccountService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.openapitools.api.AccountApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountApi {
  private static final String ACCOUNTS_PATH = "/accounts";
  private final AccountService accountService;
  private final AccountMapper accountMapper;

  public AccountController(AccountService accountService, AccountMapper accountMapper) {
    this.accountService = accountService;
    this.accountMapper = accountMapper;
  }

  @Override
  @PostMapping(value = ACCOUNTS_PATH, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
    var account = accountService.create(request);
    return ResponseEntity.of(Optional.of(accountMapper.toAccountResponse(account)));
  }

  @Override
  @GetMapping(value = ACCOUNTS_PATH)
  public ResponseEntity<List<AccountResponse>> getAccounts() {
    var response = StreamSupport.stream(accountService.findAll().spliterator(), false)
        .map(accountMapper::toAccountResponse)
        .toList();

    return ResponseEntity.of(Optional.of(response));
  }
}
