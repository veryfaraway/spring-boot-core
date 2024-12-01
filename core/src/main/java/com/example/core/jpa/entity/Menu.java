package com.example.core.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menus")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
	// 메뉴의 고유 식별자
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 메뉴 이름
	@Column(nullable = false)
	private String name;

	// 메뉴 URL
	@Column(nullable = false)
	private String url;

	// 메뉴 순서
	@Column(name = "display_order")
	private Integer displayOrder;

	// 부모 메뉴 참조
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Menu parent;

	// 자식 메뉴 목록
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Menu> children = new ArrayList<>();

	// 활성화 여부
	@Column(nullable = false)
	private boolean active = true;
}