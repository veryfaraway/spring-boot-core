package com.example.core.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserPrincipal 클래스는 Spring Security 의 UserDetails 를 구현하여
 * 사용자 인증 정보를 제공하는 역할을 합니다.
 */
@Getter
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

	private Long id; // 사용자 고유 ID
	private String username; // 사용자 이름 (로그인 ID)
	private String password; // 사용자 비밀번호
	private Collection<? extends GrantedAuthority> authorities; // 사용자 권한 목록

	@Override
	public boolean isAccountNonExpired() {
		return true; // 계정 만료 여부 (true 면 만료되지 않음)
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // 계정 잠김 여부 (true 면 잠기지 않음)
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 자격 증명 만료 여부 (true 면 만료되지 않음)
	}

	@Override
	public boolean isEnabled() {
		return true; // 계정 활성화 여부 (true 면 활성화됨)
	}
}
