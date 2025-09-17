package in.vishal.bankingapp.service;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + u.getRole().name());
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
                u.isActive(), true, true, true, Collections.singleton(auth));
    }
}
