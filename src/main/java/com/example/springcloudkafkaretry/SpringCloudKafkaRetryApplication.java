package com.example.springcloudkafkaretry;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringCloudKafkaRetryApplication implements CommandLineRunner {
  @Autowired AccountService accountService;

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudKafkaRetryApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    CompletableFuture.supplyAsync(
        () -> {
          makeFundTransfer();
          return null;
        });

    CompletableFuture.supplyAsync(
        () -> {
            makeDebitFundTransfer();
          return null;
        });
  }

  private void makeFundTransfer() {
    accountService
        .findByAccountNumber("8888")
        .ifPresentOrElse(
            account -> {
              log.info("Account found: {}", account);
              account.setAvailableBalance(
                  account.getAvailableBalance().add(BigDecimal.valueOf(100)));
              Account accountSaved = accountService.save(account);
              log.info("Account credit updated: {}", accountSaved);
            },
            () -> System.out.println("Account not found"));
  }

  private void makeDebitFundTransfer() {
    accountService
        .findByAccountNumber("8888")
        .ifPresentOrElse(
            account -> {
              log.info("Account found: {}", account);
              account.setAvailableBalance(
                  account.getAvailableBalance().subtract(BigDecimal.valueOf(100)));
              Account accountSaved = accountService.save(account);
              log.info("Account debit updated: {}", accountSaved);
            },
            () -> System.out.println("Account not found"));
  }
}
