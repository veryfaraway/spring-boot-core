package com.example.web.common.menu;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.core.jpa.entity.Menu;
import com.example.core.menu.MenuAccessType;
import com.example.core.user.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuAccessEvaluator implements PermissionEvaluator {
	private final MenuPermissionService menuPermissionService;

	@Override
	public boolean hasPermission(Authentication authentication,
		Object targetDomainObject,
		Object permission) {
		if (authentication == null || targetDomainObject == null ||
			!(authentication.getPrincipal() instanceof CustomUserDetails)) {
			return false;
		}

		Long userId = ((CustomUserDetails)authentication.getPrincipal()).getId();
		Long menuId = ((Menu)targetDomainObject).getId();
		MenuAccessType requiredAccess = MenuAccessType.valueOf(permission.toString());

		MenuAccessType userAccess =
			menuPermissionService.getUserMenuAccess(userId, menuId);

		return hasRequiredAccess(userAccess, requiredAccess);
	}

	private boolean hasRequiredAccess(MenuAccessType userAccess,
		MenuAccessType requiredAccess) {
		if (userAccess == MenuAccessType.FULL_ACCESS) {
			return true;
		}
		return userAccess == MenuAccessType.READ_ONLY_ACCESS &&
			requiredAccess == MenuAccessType.READ_ONLY_ACCESS;
	}

	@Override
	public boolean hasPermission(Authentication authentication,
		Serializable targetId,
		String targetType,
		Object permission) {
		return false;
	}

}
