package in.vishal.bankingapp.repository;

import in.vishal.bankingapp.model.Transaction;
import in.vishal.bankingapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByTimestampDesc(Account account);
}
