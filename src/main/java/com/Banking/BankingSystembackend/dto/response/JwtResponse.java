package com.Banking.BankingSystembackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private String tokenType = "Bearer";

    private String fullName;

    private String email;

    private String role;

}