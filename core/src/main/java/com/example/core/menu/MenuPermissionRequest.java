package com.example.core.menu;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 권한 설정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermissionRequest {
	@NotNull(message = "접근 권한 타입은 필수입니다")
	private MenuAccessType accessType;
}
