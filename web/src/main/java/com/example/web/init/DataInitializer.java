package com.example.web.init;

import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 테스트를 위해 초기 user 정보를 추가하는 클래스. 실제 DB 연동시에는 필요 없음.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {
	/*
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Bean
	@Profile("local")
	public CommandLineRunner initData() {
		return args -> {
			// 테스트용 사용자 생성
			userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "admin@example.com"));
			userRepository.save(new User("user1", passwordEncoder.encode("user123"), "user1@example.com"));
			userRepository.save(new User("user2", passwordEncoder.encode("user123"), "user2@example.com"));
		};
	}
	*/
}
