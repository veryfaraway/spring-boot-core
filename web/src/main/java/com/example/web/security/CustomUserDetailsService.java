package com.example.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.core.security.UserPrincipal;
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
		return userRepository.findByUsername(username)
			.map(user -> UserPrincipal.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getAuthorities())
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}

	public UserDetails loadUserById(Long id) {
		return userRepository.findById(id)
			.map(user -> UserPrincipal.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getAuthorities())
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
	}
}
