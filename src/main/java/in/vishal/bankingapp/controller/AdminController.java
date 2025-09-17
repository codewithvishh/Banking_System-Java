package in.vishal.bankingapp.controller;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.model.Role;
import in.vishal.bankingapp.service.UserService;
import in.vishal.bankingapp.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final AccountService accountService;

    public AdminController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    // create user and account for them
    @PostMapping("/create-user")
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User u = userService.register(username, password, email);
        // default role USER assigned in register; we allow admin to change role if needed
        accountService.createForUser(u);
        return "Created user id=" + u.getId();
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User update) {
        User u = userService.findByUsername(update.getUsername()).orElse(null);
        // simpler update by id
        User existing = userService.findAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow();
        existing.setEmail(update.getEmail());
        existing.setUsername(update.getUsername());
        existing.setRole(update.getRole() == null ? existing.getRole() : update.getRole());
        userService.save(existing);
        return "Updated";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "Deleted";
    }
}
