package com.born.simplepos.springSimplePos.controller;

import com.born.simplepos.springSimplePos.dto.LoginRequest;
import com.born.simplepos.springSimplePos.dto.RegisterRequest;
import com.born.simplepos.springSimplePos.entity.User;
import com.born.simplepos.springSimplePos.repository.UserRepository;
import com.born.simplepos.springSimplePos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // ✅ Create new user with only base fields
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(null)
                .lastName(null)
                .phone(null)
                .address(null)
                .dateOfBirth(null)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = JwtUtil.generateToken(user.getEmail());

        Map<String, Object> userData = Map.ofEntries(
                Map.entry("id", user.getId()),
                Map.entry("username", user.getUsername()),
                Map.entry("email", user.getEmail()),
                Map.entry("firstName", user.getFirstName()),
                Map.entry("lastName", user.getLastName()),
                Map.entry("phone", user.getPhone()),
                Map.entry("address", user.getAddress()),
                Map.entry("dateOfBirth", user.getDateOfBirth())
        );

        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "user", userData
        ));
    }

}
