package io.github.aj8gh.transfer.config;

import io.github.aj8gh.transfer.api.controller.AccountController;
import io.github.aj8gh.transfer.api.controller.TransferController;
import io.github.aj8gh.transfer.api.mapper.AccountMapper;
import io.github.aj8gh.transfer.api.mapper.TransactionMapper;
import io.github.aj8gh.transfer.api.respository.AccountRepository;
import io.github.aj8gh.transfer.api.respository.TransactionRepository;
import io.github.aj8gh.transfer.api.service.AccountService;
import io.github.aj8gh.transfer.api.service.TransferService;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  public ApiConfig(TransactionRepository transactionRepository,
                   AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }

  @Bean
  AccountController accountController() {
    return new AccountController(accountService(), accountMapper());
  }

  @Bean
  AccountService accountService() {
    return new AccountService(accountRepository);
  }

  @Bean
  AccountMapper accountMapper() {
    return Mappers.getMapper(AccountMapper.class);
  }

  @Bean
  TransferController transactionController() {
    return new TransferController(transactionService(), transactionMapper());
  }

  @Bean
  TransferService transactionService() {
    return new TransferService(transactionRepository, accountService());
  }

  @Bean
  TransactionMapper transactionMapper() {
    return Mappers.getMapper(TransactionMapper.class);
  }
}
