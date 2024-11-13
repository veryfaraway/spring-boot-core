package com.example.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.security.JwtTokenProvider;
import com.example.web.model.dto.LoginRequest;
import com.example.web.model.dto.SignupRequest;
import com.example.web.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider tokenProvider;

	/**
	 * 로그인 요청을 처리합니다.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		String jwt = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok(jwt);
	}

	/**
	 * 회원가입 요청을 처리합니다.
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		authService.registerUser(signUpRequest);
		return ResponseEntity.ok("User registered successfully");
	}
}
