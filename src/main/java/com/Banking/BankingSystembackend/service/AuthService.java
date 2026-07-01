package com.Banking.BankingSystembackend.service;

import com.Banking.BankingSystembackend.dto.request.LoginRequest;
import com.Banking.BankingSystembackend.dto.request.RegisterRequest;
import com.Banking.BankingSystembackend.dto.response.JwtResponse;

public interface AuthService {

    JwtResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

}