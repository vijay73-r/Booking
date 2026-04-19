package com.smartbooking.smart_booking.auth.service;

import com.smartbooking.smart_booking.auth.dto.AuthResponse;
import com.smartbooking.smart_booking.auth.dto.LoginRequest;
import com.smartbooking.smart_booking.auth.dto.RegisterRequest;
import com.smartbooking.smart_booking.auth.entity.Role;
import com.smartbooking.smart_booking.auth.entity.User;
import com.smartbooking.smart_booking.auth.repository.UserRepository;
import com.smartbooking.smart_booking.auth.security.JwtUtil;
import com.smartbooking.smart_booking.common.exception.ConflictException;
import com.smartbooking.smart_booking.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid credentials"));

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
