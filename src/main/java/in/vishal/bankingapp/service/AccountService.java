package in.vishal.bankingapp.service;

import in.vishal.bankingapp.model.Account;
import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createForUser(User user) {
        Account a = Account.builder()
                .user(user)
                .accountNumber("ACC" + System.currentTimeMillis())
                .balance(0.0)
                .frozen(false)
                .build();
        return accountRepository.save(a);
    }

    public List<Account> getByUser(User user) {
        return accountRepository.findByUser(user);
    }

    public List<Account> getAll() { return accountRepository.findAll(); }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account save(Account a) { return accountRepository.save(a); }
}
