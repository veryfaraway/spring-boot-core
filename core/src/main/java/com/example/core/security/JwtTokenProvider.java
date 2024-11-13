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
	@Value("${app.jwtSecret}")
	private String jwtSecret; // JWT 서명에 사용되는 비밀 키, openssl rand -hex 64

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs; // JWT 의 만료 시간 (밀리초)

	/**
	 * 사용자 ID로 JWT 토큰을 생성합니다.
	 *
	 * @param userId 사용자 ID
	 * @return 생성된 JWT 토큰
	 */
	public String generateToken(Long userId) {
		Instant now = Instant.now();
		Instant expiryDate = now.plus(jwtExpirationInMs, ChronoUnit.MILLIS);

		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		return Jwts.builder()
			.subject(userId.toString())
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiryDate))
			.signWith(key)
			.compact();
	}

	/**
	 * JWT 토큰으로부터 사용자 ID를 추출합니다.
	 *
	 * @param token JWT 토큰 문자열
	 * @return 추출된 사용자 ID
	 */
	public Long getUserIdFromJWT(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		Claims claims = Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return Long.parseLong(claims.getSubject());
	}

	/**
	 * JWT 토큰에서 사용자 ID를 추출합니다.
	 *
	 * @param token JWT 토큰
	 * @return 추출된 사용자 ID
	 */
	public Long getUserIdFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

		Claims claims = Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return Long.parseLong(claims.getSubject());
	}

	/**
	 * JWT 토큰의 유효성을 검증합니다.
	 *
	 * @param authToken 검증할 JWT 토큰 문자열
	 * @return 유효한 경우 true, 그렇지 않으면 false
	 */
	public boolean validateToken(String authToken) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
			Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}
}
