package io.github.aj8gh.transfer.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  void debitHappyPath() {
    var account = Account.builder().balance(100).currency(Currency.GBP).build();
    account.debit(100);
    assertEquals(0, account.getBalance());
  }

  @Test
  void debitInsufficientBalance() {
    var account = Account.builder().balance(100).currency(Currency.GBP).build();
    assertThrows(IllegalArgumentException.class, () -> account.debit(200));
  }

  @Test
  void creditHappyPath() {
    var account = Account.builder().balance(100).currency(Currency.GBP).build();
    account.credit(100);
    assertEquals(200, account.getBalance());
  }
}
