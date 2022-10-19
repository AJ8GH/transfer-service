package io.github.aj8gh.transfer.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.domain.TransferResponse;
import io.github.aj8gh.transfer.api.mapper.TransactionMapper;
import io.github.aj8gh.transfer.api.service.TransferService;
import java.util.Optional;
import org.openapitools.api.TransferApi;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController implements TransferApi {
  private static final String TRANSFER_PATH = "/transfer";
  private final TransferService transferService;
  private final TransactionMapper transactionMapper;

  public TransferController(TransferService transferService,
                            TransactionMapper transactionMapper) {
    this.transferService = transferService;
    this.transactionMapper = transactionMapper;
  }

  @Transactional
  @PostMapping(value = TRANSFER_PATH, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<TransferResponse> transfer(@RequestBody final TransferRequest request) {
    try {
      var response = transactionMapper.toTransferResponse(transferService.transfer(request));
      return ResponseEntity.of(Optional.of(response));
    } catch (Exception e) {
      return null;
    }
  }
}

























/*





 * */
