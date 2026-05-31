package com.example.openspecdemo.controller;

import com.example.openspecdemo.generated.api.AuthApi;
import com.example.openspecdemo.generated.model.LoginRequest;
import com.example.openspecdemo.generated.model.LoginResponse;
import com.example.openspecdemo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(@Valid LoginRequest loginRequest) {
        com.example.openspecdemo.dto.auth.LoginRequest serviceRequest = new com.example.openspecdemo.dto.auth.LoginRequest();
        serviceRequest.setUsername(loginRequest.getUsername());
        serviceRequest.setPassword(loginRequest.getPassword());

        com.example.openspecdemo.dto.auth.LoginResponse serviceResponse = authService.login(serviceRequest);

        LoginResponse response = new LoginResponse();
        response.setToken(serviceResponse.getToken());
        response.setTokenType(serviceResponse.getTokenType());
        return ResponseEntity.ok(response);
    }
}
