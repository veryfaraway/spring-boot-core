package com.example.core.menu;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermissionDTO {
	private Long id;
	private Long menuId;
	private Long userId;
	private Long userGroupId;
	private MenuAccessType accessType;

	@AssertTrue(message = "사용자 또는 그룹 중 하나만 설정되어야 합니다")
	public boolean isValidPermissionTarget() {
		return (userId != null && userGroupId == null) ||
			(userId == null && userGroupId != null);
	}
}
