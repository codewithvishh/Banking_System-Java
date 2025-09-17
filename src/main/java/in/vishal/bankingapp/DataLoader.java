package in.vishal.bankingapp;

import in.vishal.bankingapp.model.Role;
import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User a = User.builder()
                    .username("admin")
                    .password(encoder.encode("adminpass"))
                    .email("admin@bank.com")
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(a);
        }
        if (userRepository.findByUsername("manager").isEmpty()) {
            User m = User.builder()
                    .username("manager")
                    .password(encoder.encode("managerpass"))
                    .email("manager@bank.com")
                    .role(Role.MANAGER)
                    .active(true)
                    .build();
            userRepository.save(m);
        }
    }
}
