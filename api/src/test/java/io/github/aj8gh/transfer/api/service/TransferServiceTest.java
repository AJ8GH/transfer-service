package io.github.aj8gh.transfer.api.service;

import static io.github.aj8gh.transfer.api.domain.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import io.github.aj8gh.transfer.TestMockSetUp;
import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.respository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class TransferServiceTest extends TestMockSetUp {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private AccountService accountService;
  private TransferService transferService;

  @BeforeEach
  void setUp() {
    transferService = new TransferService(transactionRepository, accountService);
  }

  @Test
  void transferHappyPath() {
    var request = new TransferRequest()
        .sourceAccountId("123")
        .targetAccountId("987")
        .amount(100L)
        .currency(GBP);

    var actual = transferService.transfer(request);

    assertEquals(request.getSourceAccountId(), actual.getSourceAccountId());
    assertEquals(request.getTargetAccountId(), actual.getTargetAccountId());
    assertEquals(request.getCurrency(), actual.getCurrency());
    assertEquals(request.getAmount(), actual.getAmount());

    verify(accountService).transfer(request);
    verify(transactionRepository).save(actual);
  }
}
