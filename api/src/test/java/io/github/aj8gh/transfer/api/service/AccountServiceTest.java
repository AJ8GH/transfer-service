package io.github.aj8gh.transfer.api.service;

import static io.github.aj8gh.transfer.api.domain.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.aj8gh.transfer.TestMockSetUp;
import io.github.aj8gh.transfer.api.domain.Account;
import io.github.aj8gh.transfer.api.domain.CreateAccountRequest;
import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.respository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class AccountServiceTest extends TestMockSetUp {

  @Mock
  private AccountRepository accountRepository;
  private AccountService accountService;

  @BeforeEach
  void setUp() {
    accountService = new AccountService(accountRepository);
  }

  @Test
  void transferHappyPath() {
    var sourceAccount = Account.builder().currency(GBP).balance(100).build();
    var targetAccount = Account.builder().currency(GBP).balance(0).build();
    var request = new TransferRequest()
        .sourceAccountId(sourceAccount.getId())
        .targetAccountId(targetAccount.getId())
        .amount(100L);

    when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
    when(accountRepository.findById(targetAccount.getId())).thenReturn(Optional.of(targetAccount));
    assertEquals(100, sourceAccount.getBalance());
    assertEquals(0, targetAccount.getBalance());

    accountService.transfer(request);

    assertEquals(0, sourceAccount.getBalance());
    assertEquals(100, targetAccount.getBalance());
    verify(accountRepository).save(sourceAccount);
    verify(accountRepository).save(targetAccount);
  }

  @Test
  void transferInsufficientBalance() {
    var sourceAccount = Account.builder().currency(GBP).balance(100).build();
    var sourceAccountId = sourceAccount.getId();
    var request = new TransferRequest()
        .sourceAccountId(sourceAccount.getId())
        .targetAccountId("987")
        .amount(200L);

    when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
    assertThrows(IllegalArgumentException.class, () -> accountService.transfer(request));
  }

  @Test
  void create() {
    var createAccountRequest = new CreateAccountRequest()
        .currency(GBP)
        .initialBalance(10L);

    var actual = accountService.create(createAccountRequest);

    assertEquals(10L, actual.getBalance());
    assertEquals(GBP, actual.getCurrency());
    assertNotNull(actual.getId());

    verify(accountRepository).save(actual);
  }
}
