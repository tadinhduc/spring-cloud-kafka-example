package com.example.springcloudkafkaretry;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired private AccountService accountService;

  @GetMapping
  public List<Account> getAllAccounts() {
    return accountService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    Optional<Account> account = accountService.findById(id);
    return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Account createAccount(@RequestBody Account account) {
    return accountService.save(account);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Account> updateAccount(
      @PathVariable Long id, @RequestBody Account accountDetails) {
    Optional<Account> account = accountService.findById(id);
    if (account.isPresent()) {
      Account updatedAccount = account.get();
      updatedAccount.setAccountNumber(accountDetails.getAccountNumber());
      updatedAccount.setAvailableBalance(accountDetails.getAvailableBalance());
      return ResponseEntity.ok(accountService.save(updatedAccount));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
    if (accountService.findById(id).isPresent()) {
      accountService.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
