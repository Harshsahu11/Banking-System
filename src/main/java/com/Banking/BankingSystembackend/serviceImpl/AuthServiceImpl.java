package com.Banking.BankingSystembackend.serviceImpl;

import com.Banking.BankingSystembackend.dto.request.LoginRequest;
import com.Banking.BankingSystembackend.dto.request.RegisterRequest;
import com.Banking.BankingSystembackend.dto.response.JwtResponse;
import com.Banking.BankingSystembackend.entity.Role;
import com.Banking.BankingSystembackend.entity.User;
import com.Banking.BankingSystembackend.exception.ResourceNotFoundException;
import com.Banking.BankingSystembackend.repository.UserRepository;
import com.Banking.BankingSystembackend.security.JwtService;
import com.Banking.BankingSystembackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public JwtResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        com.Banking.BankingSystembackend.entity.User user =
                com.Banking.BankingSystembackend.entity.User.builder()
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.ROLE_USER)
                        .build();

        userRepository.save(user);

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();

        String token = jwtService.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        com.Banking.BankingSystembackend.entity.User user =
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User not found"));

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();

        String token = jwtService.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}