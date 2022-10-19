package io.github.aj8gh.transfer.api.mapper;

import io.github.aj8gh.transfer.api.domain.Transaction;
import io.github.aj8gh.transfer.api.domain.TransferResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {

  TransferResponse toTransferResponse(Transaction transaction);
}
