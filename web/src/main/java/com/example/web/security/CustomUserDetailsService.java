package com.example.web.security;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.core.security.UserPrincipal;
import com.example.web.jpa.entity.User;
import com.example.web.jpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 상세 정보 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		boolean needOtpVerification = user.getLastOtpVerification() == null ||
			user.getLastOtpVerification().isBefore(LocalDateTime.now().minusDays(1));

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getUsername())
			.password(user.getPassword())
			.authorities("USER")
			.accountLocked(needOtpVerification)
			.build();
	}

	public void updateLastOtpVerification(String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		user.setLastOtpVerification(LocalDateTime.now());
		userRepository.save(user);
	}
}
