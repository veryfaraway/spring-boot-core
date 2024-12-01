package com.example.web.common.menu;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.menu.MenuAccessType;
import com.example.core.menu.MenuPermissionDTO;
import com.example.core.menu.MenuPermissionRequest;
import com.example.core.user.CustomUserDetails;
import com.example.web.common.dto.ApiResponse;
import com.example.web.common.exceptions.GroupNotFoundException;
import com.example.web.common.exceptions.MenuNotFoundException;
import com.example.web.common.exceptions.UserNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/menu-permissions")
@RequiredArgsConstructor
public class MenuPermissionController {
	private final MenuPermissionService menuPermissionService;

	/**
	 * 메뉴 접근 권한 조회
	 */
	@GetMapping("/{menuId}/access")
	public ResponseEntity<MenuAccessType> checkAccess(
		@PathVariable Long menuId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			MenuAccessType accessType = menuPermissionService
				.getUserMenuAccess(userDetails.getId(), menuId);
			return ResponseEntity.ok(accessType);
		} catch (MenuNotFoundException e) {
			log.error("Menu not found: {}", menuId, e);
			throw e;
		}
	}

	/**
	 * 사용자 메뉴 권한 설정
	 */
	@PostMapping("/{menuId}/user/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> setUserPermission(
		@PathVariable Long menuId,
		@PathVariable Long userId,
		@RequestBody @Valid MenuPermissionRequest request) {
		try {
			menuPermissionService.setUserMenuPermission(userId, menuId, request.getAccessType());
			return ResponseEntity.ok(new ApiResponse("사용자 권한이 설정되었습니다."));
		} catch (UserNotFoundException | MenuNotFoundException e) {
			log.error("Error setting user permission: {}", e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 그룹 메뉴 권한 설정
	 */
	@PostMapping("/{menuId}/group/{groupId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> setGroupPermission(
		@PathVariable Long menuId,
		@PathVariable Long groupId,
		@RequestBody @Valid MenuPermissionRequest request) {
		try {
			menuPermissionService.setGroupMenuPermission(groupId, menuId, request.getAccessType());
			return ResponseEntity.ok(new ApiResponse("그룹 권한이 설정되었습니다."));
		} catch (GroupNotFoundException | MenuNotFoundException e) {
			log.error("Error setting group permission: {}", e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 메뉴의 모든 권한 조회
	 */
	@GetMapping("/{menuId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<MenuPermissionDTO>> getMenuPermissions(
		@PathVariable Long menuId) {
		List<MenuPermissionDTO> permissions = menuPermissionService.getMenuPermissions(menuId);
		return ResponseEntity.ok(permissions);
	}

	/**
	 * 사용자의 모든 메뉴 권한 조회
	 */
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
	public ResponseEntity<List<MenuPermissionDTO>> getUserPermissions(
		@PathVariable Long userId) {
		List<MenuPermissionDTO> permissions = menuPermissionService.getUserPermissions(userId);
		return ResponseEntity.ok(permissions);
	}

	/**
	 * 그룹의 모든 메뉴 권한 조회
	 */
	@GetMapping("/group/{groupId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<MenuPermissionDTO>> getGroupPermissions(
		@PathVariable Long groupId) {
		List<MenuPermissionDTO> permissions = menuPermissionService.getGroupPermissions(groupId);
		return ResponseEntity.ok(permissions);
	}

	/**
	 * 권한 삭제
	 */
	@DeleteMapping("/{permissionId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePermission(
		@PathVariable Long permissionId) {
		menuPermissionService.deletePermission(permissionId);
		return ResponseEntity.ok(new ApiResponse("권한이 삭제되었습니다."));
	}
}



