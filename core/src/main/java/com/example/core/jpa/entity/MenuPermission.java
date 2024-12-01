package com.example.core.jpa.entity;

import com.example.core.menu.MenuAccessType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_permissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 메뉴 참조
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;

	// 사용자 참조 (null 가능 - 그룹 권한인 경우)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 사용자 그룹 참조 (null 가능 - 개별 사용자 권한인 경우)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private UserGroup userGroup;

	// 접근 권한 타입
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MenuAccessType accessType;
}