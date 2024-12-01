package com.example.core.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuAccessType {
	NO_ACCESS("접근 불가"),
	READ_ONLY_ACCESS("읽기 전용"),
	FULL_ACCESS("전체 접근");

	private final String description;
}
