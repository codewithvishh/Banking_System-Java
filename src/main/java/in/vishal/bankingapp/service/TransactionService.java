package in.vishal.bankingapp.service;

import in.vishal.bankingapp.model.Account;
import in.vishal.bankingapp.model.Transaction;
import in.vishal.bankingapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Transactional
    public Transaction deposit(Long accountId, Double amount) {
        Account a = accountService.findById(accountId);
        if (a.isFrozen()) throw new RuntimeException("Account frozen");
        a.setBalance(a.getBalance() + amount);
        accountService.save(a);

        Transaction t = Transaction.builder().type("DEPOSIT").amount(amount).account(a).build();
        return transactionRepository.save(t);
    }

    @Transactional
    public Transaction withdraw(Long accountId, Double amount) {
        Account a = accountService.findById(accountId);
        if (a.isFrozen()) throw new RuntimeException("Account frozen");
        if (a.getBalance() < amount) throw new RuntimeException("Insufficient funds");
        a.setBalance(a.getBalance() - amount);
        accountService.save(a);

        Transaction t = Transaction.builder().type("WITHDRAW").amount(amount).account(a).build();
        return transactionRepository.save(t);
    }

    public List<Transaction> getByAccount(Long accountId) {
        Account a = accountService.findById(accountId);
        return transactionRepository.findByAccountOrderByTimestampDesc(a);
    }
}
