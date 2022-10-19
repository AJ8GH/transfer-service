package io.github.aj8gh.transfer.api.controller;

import static io.github.aj8gh.transfer.api.domain.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.aj8gh.transfer.TestMockSetUp;
import io.github.aj8gh.transfer.api.domain.Transaction;
import io.github.aj8gh.transfer.api.domain.TransferRequest;
import io.github.aj8gh.transfer.api.domain.TransferResponse;
import io.github.aj8gh.transfer.api.mapper.TransactionMapper;
import io.github.aj8gh.transfer.api.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TransferControllerTest extends TestMockSetUp {

  @Mock
  private TransferService transferService;
  @Mock
  private TransactionMapper transactionMapper;
  private ObjectMapper mapper;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    var controller = new TransferController(transferService, transactionMapper);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void transferHappyPath() throws Exception {
    var request = new TransferRequest()
        .sourceAccountId("123")
        .targetAccountId("987")
        .amount(100L)
        .currency(GBP);

    var expectedTransaction = Transaction.builder()
        .sourceAccountId("123")
        .targetAccountId("987")
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .build();

    var expectedResponse = new TransferResponse()
        .amount(expectedTransaction.getAmount())
        .currency(expectedTransaction.getCurrency())
        .id(expectedTransaction.getId())
        .sourceAccountId(expectedTransaction.getSourceAccountId())
        .targetAccountId(expectedTransaction.getTargetAccountId())
        .createdAt(expectedTransaction.getCreatedAt());

    when(transferService.transfer(request)).thenReturn(expectedTransaction);
    when(transactionMapper.toTransferResponse(expectedTransaction)).thenReturn(expectedResponse);

    var result = mockMvc.perform(post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn();

    var actual = mapper.readValue(
        result.getResponse().getContentAsString(),
        TransferResponse.class);

    verify(transferService).transfer(request);
    verify(transactionMapper).toTransferResponse(expectedTransaction);

    assertEquals(expectedResponse, actual);
  }

  @Test
  void transferInsufficientBalance() throws Exception {
    var request = new TransferRequest()
        .sourceAccountId("123")
        .targetAccountId("987")
        .amount(100L);

    doThrow(new IllegalArgumentException()).when(transferService).transfer(request);

    var result = mockMvc.perform(post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andReturn();
  }
}
