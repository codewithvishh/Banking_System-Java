package in.vishal.bankingapp.controller;

import in.vishal.bankingapp.model.User;
import in.vishal.bankingapp.payload.LoginRequest;
import in.vishal.bankingapp.payload.LoginResponse;
import in.vishal.bankingapp.payload.RegisterRequest;
import in.vishal.bankingapp.security.JwtUtil;
import in.vishal.bankingapp.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, UserService userService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest req) {
        User u = userService.register(req.getUsername(), req.getPassword(), req.getEmail());
        return "Registered. userId=" + u.getId();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid credentials");
        }
        User u = userService.findByUsername(req.getUsername()).orElseThrow();
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", u.getRole().name());
        claims.put("userId", u.getId());
        String token = jwtUtil.generateToken(u.getUsername(), claims);
        return new LoginResponse(token, u.getRole().name());
    }
}
