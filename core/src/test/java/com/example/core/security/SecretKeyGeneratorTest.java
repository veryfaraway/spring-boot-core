package com.example.core.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.Base64;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class SecretKeyGeneratorTest {
	@RepeatedTest(10)
	public void testGenerateSecretKey() {
		String secretKey = generateSecretKey();

		// 생성된 키가 null이 아닌지 확인
		assertNotNull(secretKey);

		// Base64로 디코딩했을 때 32바이트인지 확인
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		assertEquals(32, decodedKey.length);

		// 생성된 키 출력 (선택사항)
		System.out.println("Generated Secret Key: " + secretKey);
	}

	private String generateSecretKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];
		secureRandom.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}
}
