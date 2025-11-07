package com.bite.service;

import com.bite.dto.AuthResponse;
import com.bite.dto.LoginRequest;
import com.bite.dto.RegisterRequest;
import com.bite.model.User;
import com.bite.model.UserRole;
import com.bite.repository.UserRepository;
import com.bite.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole() != null ? request.getRole() : UserRole.CUSTOMER);

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name(), user.getId());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole(), user.getId());
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name(), user.getId());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole(), user.getId());
    }
}

