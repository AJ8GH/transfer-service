package io.github.aj8gh.transfer.api.controller;

import static io.github.aj8gh.transfer.api.domain.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.aj8gh.transfer.TestMockSetUp;
import io.github.aj8gh.transfer.api.domain.Account;
import io.github.aj8gh.transfer.api.domain.AccountResponse;
import io.github.aj8gh.transfer.api.domain.CreateAccountRequest;
import io.github.aj8gh.transfer.api.mapper.AccountMapper;
import io.github.aj8gh.transfer.api.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AccountControllerTest extends TestMockSetUp {

  private static final String ACCOUNTS_PATH = "/accounts";

  @Mock
  private AccountService accountService;
  @Mock
  private AccountMapper accountMapper;
  private ObjectMapper mapper;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    var controller = new AccountController(accountService, accountMapper);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void createHappyPath() throws Exception {
    var request = new CreateAccountRequest()
        .initialBalance(1000L)
        .currency(GBP);

    var account = Account.builder()
        .balance(request.getInitialBalance())
        .currency(request.getCurrency())
        .build();

    when(accountService.create(request)).thenReturn(account);
    var expected = Mappers.getMapper(AccountMapper.class).toAccountResponse(account);
    when(accountMapper.toAccountResponse(account)).thenReturn(expected);

    var response = mockMvc.perform(post(ACCOUNTS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn();

    var actual =
        mapper.readValue(response.getResponse().getContentAsString(), AccountResponse.class);

    assertEquals(expected, actual);
    verify(accountService).create(request);
    verify(accountMapper).toAccountResponse(account);
  }

  @Test
  void createAccountExists() throws Exception {
    var request = new CreateAccountRequest()
        .initialBalance(-1000L);


    var account = Account.builder()
        .balance(request.getInitialBalance())
        .currency(GBP)
        .build();

    when(accountService.create(request)).thenReturn(account);
    var expected = Mappers.getMapper(AccountMapper.class).toAccountResponse(account);
    when(accountMapper.toAccountResponse(account)).thenReturn(expected);

    var response = mockMvc.perform(post(ACCOUNTS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn();

    var actual =
        mapper.readValue(response.getResponse().getContentAsString(), AccountResponse.class);

    assertEquals(expected, actual);
    verify(accountService).create(request);
    verify(accountMapper).toAccountResponse(account);
  }
}
