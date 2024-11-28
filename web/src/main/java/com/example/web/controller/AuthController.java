package com.example.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.web.model.dto.AuthResponse;
import com.example.web.model.dto.LoginRequest;
import com.example.web.model.dto.OtpRequest;
import com.example.web.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			log.info("Received login request for user: {}", loginRequest.getUsername());
			AuthResponse response = authService.login(loginRequest);
			log.info("Login successful for user: {}", loginRequest.getUsername());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Login failed: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Authentication failed: " + e.getMessage());
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<AuthResponse> verifyOtp(@RequestBody OtpRequest otpRequest) {
		return ResponseEntity.ok(authService.verifyOtp(otpRequest));
	}
}
