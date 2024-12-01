package com.example.core.menu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
	private Long id;
	private String name;
	private String url;
	private Integer displayOrder;
	private boolean active;
	private Long parentId;
	private MenuAccessType accessType;
	private List<MenuDTO> children;

	// URL이 유효한지 검증하는 메서드
	@AssertTrue(message = "유효하지 않은 URL 형식입니다")
	public boolean isValidUrl() {
		if (url == null) return false;
		try {
			new URL(url);
			return true;
		} catch (MalformedURLException e) {
			return url.startsWith("/"); // 상대 경로 허용
		}
	}
}
