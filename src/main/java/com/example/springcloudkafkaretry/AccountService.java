package com.example.springcloudkafkaretry;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  @Autowired private AccountRepository accountRepository;

  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  public Optional<Account> findById(Long id) {
    return accountRepository.findById(id);
  }

  @Transactional(isolation = Isolation.READ_UNCOMMITTED)
  public Account save(Account account) {
    return accountRepository.save(account);
  }

  public void deleteById(Long id) {
    accountRepository.deleteById(id);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public Optional<Account> findByAccountNumber(String accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber);
  }
}
