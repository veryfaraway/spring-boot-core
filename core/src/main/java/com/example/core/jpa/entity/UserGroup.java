package com.example.core.jpa.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(length = 1000)
	private String description;

	// 그룹에 속한 사용자들
	@ManyToMany(mappedBy = "userGroups")
	private Set<User> users = new HashSet<>();

	// 그룹의 메뉴 권한
	@OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
	private Set<MenuPermission> menuPermissions = new HashSet<>();

	// 그룹의 권한 목록
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "group_authorities",
		joinColumns = @JoinColumn(name = "group_id")
	)

	@Column(name = "authority")
	private Set<String> authorities = new HashSet<>();

	// 권한 변환 메서드
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toSet());
	}

	// 그룹 활성화 여부
	@Column(nullable = false)
	private boolean enabled = true;
}
