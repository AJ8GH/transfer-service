package io.github.aj8gh.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(classes = RestController.class))
public class TransferServiceApplication {

  public static void main(String... args) {
    SpringApplication.run(TransferServiceApplication.class);
  }
}
