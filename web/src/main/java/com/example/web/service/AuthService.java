package com.example.web.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.security.JwtTokenProvider;
import com.example.web.jpa.entity.User;
import com.example.web.jpa.repository.UserRepository;
import com.example.web.model.dto.SignupRequest;

import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider tokenProvider;

	/**
	 * 사용자 인증 및 JWT 토큰 생성
	 *
	 * @param username 사용자 이름
	 * @param password 비밀번호
	 * @return JWT 토큰
	 */
	public String authenticateUser(String username, String password) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return tokenProvider.generateToken(((User)authentication.getPrincipal()).getId());
	}

	/**
	 * 새 사용자 등록
	 *
	 * @param signUpRequest 회원가입 요청 정보
	 */
	@Transactional
	public void registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new RuntimeException("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new RuntimeException("Error: Email is already in use!");
		}

		// 새 사용자 생성
		User user = User.builder()
			.username(signUpRequest.getUsername())
			.email(signUpRequest.getEmail())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.build();

		userRepository.save(user);
	}
}
