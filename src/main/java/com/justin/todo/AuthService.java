package com.justin.todo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> register(String username, String password) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body("Username cannot be blank");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(username)));
    }

    public ResponseEntity<?> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(username))))
                .orElse(ResponseEntity.status(401).build());
    }
}