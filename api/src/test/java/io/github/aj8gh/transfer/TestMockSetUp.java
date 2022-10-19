package io.github.aj8gh.transfer;

import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestMockSetUp {

  private AutoCloseable closeable;

  @BeforeEach
  void setUpMocks() {
    closeable = openMocks(this);
  }

  @AfterEach
  void tearDownMocks() throws Exception {
    closeable.close();
  }
}
