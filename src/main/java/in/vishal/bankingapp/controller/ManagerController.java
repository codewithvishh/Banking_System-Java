package in.vishal.bankingapp.controller;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.service.UserService;
import in.vishal.bankingapp.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final UserService userService;
    private final AccountService accountService;

    public ManagerController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/accounts")
    public List<?> allAccounts() {
        return accountService.getAll();
    }

    @PutMapping("/users/{id}/freeze")
    public String freezeUser(@PathVariable Long id, @RequestParam boolean freeze) {
        User u = userService.findAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow();
        u.setActive(!freeze ? true : false);
        userService.save(u);
        return freeze ? "User frozen" : "User unfrozen";
    }

    // Freeze/unfreeze account
    @PutMapping("/accounts/{id}/freeze")
    public String freezeAccount(@PathVariable Long id, @RequestParam boolean freeze) {
        var acc = accountService.findById(id);
        acc.setFrozen(freeze);
        accountService.save(acc);
        return freeze ? "Account frozen" : "Account unfrozen";
    }
}
