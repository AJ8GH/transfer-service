package io.github.aj8gh.transfer.api.mapper;

import io.github.aj8gh.transfer.api.domain.Account;
import io.github.aj8gh.transfer.api.domain.AccountResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

  AccountResponse toAccountResponse(Account account);
}
