package com.Banking.BankingSystembackend.controller;

import com.Banking.BankingSystembackend.dto.request.LoginRequest;
import com.Banking.BankingSystembackend.dto.request.RegisterRequest;
import com.Banking.BankingSystembackend.dto.response.JwtResponse;
import com.Banking.BankingSystembackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register User",
            description = "Register a new user in the Banking System."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        JwtResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "User Login",
            description = "Authenticate user and return JWT token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {

        JwtResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}