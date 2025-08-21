package com.auth_service.service;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.SignupRequest;
import com.auth_service.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    public ResponseEntity<ApiResponse> signUp(SignupRequest request);

    public ResponseEntity<ApiResponse> login(LoginRequest request);
}
