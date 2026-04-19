package com.smartbooking.smart_booking.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private String email;
    private String role;
}
