package in.vishal.bankingapp.service;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.model.Role;
import in.vishal.bankingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        User u = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(Role.USER)
                .active(true)
                .build();
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User u) { return userRepository.save(u); }

    public List<User> findAll() { return userRepository.findAll(); }

    public void deleteById(Long id) { userRepository.deleteById(id); }
}
