package in.vishal.bankingapp.controller;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.model.Account;
import in.vishal.bankingapp.service.AccountService;
import in.vishal.bankingapp.service.TransactionService;
import in.vishal.bankingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public UserController(UserService userService, AccountService accountService,
                          TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    private User getCurrentUser(Authentication auth) {
        return userService.findByUsername(auth.getName()).orElseThrow();
    }

    @PostMapping("/deposit")
    public String deposit(Authentication auth, @RequestParam Long accountId, @RequestParam Double amount) {
        User u = getCurrentUser(auth);
        // permission: ensure account belongs to user
        Account a = accountService.findById(accountId);
        if (!a.getUser().getId().equals(u.getId())) return "Account does not belong to user";
        transactionService.deposit(accountId, amount);
        return "Deposit successful";
    }

    @PostMapping("/withdraw")
    public String withdraw(Authentication auth, @RequestParam Long accountId, @RequestParam Double amount) {
        User u = getCurrentUser(auth);
        Account a = accountService.findById(accountId);
        if (!a.getUser().getId().equals(u.getId())) return "Account does not belong to user";
        transactionService.withdraw(accountId, amount);
        return "Withdraw successful";
    }

    @GetMapping("/balance")
    public Double balance(Authentication auth) {
        User u = getCurrentUser(auth);
        List<Account> accounts = accountService.getByUser(u);
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    @GetMapping("/transactions")
    public List<?> transactions(Authentication auth, @RequestParam Long accountId) {
        User u = getCurrentUser(auth);
        Account a = accountService.findById(accountId);
        if (!a.getUser().getId().equals(u.getId())) throw new RuntimeException("Forbidden");
        return transactionService.getByAccount(accountId);
    }
}
