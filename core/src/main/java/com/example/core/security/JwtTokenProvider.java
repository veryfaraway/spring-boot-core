package com.example.core.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰 생성 및 검증을 담당하는 클래스
 */
@Slf4j
@Component
public class JwtTokenProvider {
	private final SecretKey secretKey;
	private final long tokenValidityInMilliseconds;

	/**
	 *
	 * @param jwtSecret    JWT 서명에 사용되는 비밀 키, openssl rand -hex 64
	 * @param tokenValidityInMilliseconds    JWT 의 만료 시간 (밀리초)
	 */
	public JwtTokenProvider(
		@Value("${jwt.secret}") String jwtSecret,
		@Value("${jwt.expiration}") int tokenValidityInMilliseconds) {
		this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
	}

	/**
	 * 사용자 ID를 기반으로 JWT 토큰을 생성합니다.
	 */
	public String createToken(String username, boolean otpVerified) {
		Instant now = Instant.now();
		Instant validity = now.plus(tokenValidityInMilliseconds, ChronoUnit.MILLIS);

		return Jwts.builder()
			.subject(username)
			.claim("otpVerified", otpVerified)
			.issuedAt(Date.from(now))
			.expiration(Date.from(validity))
			.signWith(secretKey)
			.compact();
	}

	/**
	 * JWT 토큰의 유효성을 검증합니다.
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * 토큰에서 사용자 이름을 추출합니다.
	 */
	public String getUsername(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}

	/**
	 * 토큰에서 OTP 인증 여부를 확인합니다.
	 */
	public boolean isOtpVerified(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("otpVerified", Boolean.class);
	}

}
