package com.example.web.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.security.JwtTokenProvider;
import com.example.web.jpa.entity.User;
import com.example.web.jpa.repository.UserRepository;
import com.example.web.model.dto.AuthResponse;
import com.example.web.model.dto.LoginRequest;
import com.example.web.model.dto.OtpRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증 관련 서비스를 처리하는 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;
	private final UserRepository userRepository;

	@Value("${app.otp.code}")
	private String validOtpCode;

	/**
	 * 로그인 처리를 수행합니다.
	 */
	public AuthResponse login(LoginRequest loginRequest) {
		log.info("Attempting login for user: {}", loginRequest.getUsername());
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(),
					loginRequest.getPassword()
				)
			);

			log.info("User {} successfully authenticated", loginRequest.getUsername());

			String token = tokenProvider.createToken(authentication.getName(), false);

			return new AuthResponse(token, "OTP verification required");
		} catch (BadCredentialsException e) {
			log.error("Login failed for user {}: Invalid credentials", loginRequest.getUsername());
			throw new RuntimeException("Invalid username or password");
		} catch (Exception e) {
			log.error("Login failed for user {}: {}", loginRequest.getUsername(), e.getMessage());
			throw new RuntimeException("Login failed: " + e.getMessage());
		}
	}

	/**
	 * OTP 검증을 수행합니다.
	 */
	@Transactional
	public AuthResponse verifyOtp(OtpRequest otpRequest) {
		if (!validOtpCode.equals(otpRequest.getOtpCode())) {
			throw new RuntimeException("Invalid OTP code");
		}

		User user = userRepository.findByUsername(otpRequest.getUsername())
			.orElseThrow(() -> new RuntimeException("User not found"));

		user.setLastOtpVerification(LocalDateTime.now());
		userRepository.save(user);

		String token = tokenProvider.createToken(user.getUsername(), true);

		return new AuthResponse(token, "Authentication successful");
	}
}
