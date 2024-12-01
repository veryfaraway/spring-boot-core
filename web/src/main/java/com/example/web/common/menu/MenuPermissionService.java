package com.example.web.common.menu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.jpa.entity.Menu;
import com.example.core.menu.MenuAccessType;
import com.example.core.jpa.entity.MenuPermission;
import com.example.core.menu.MenuPermissionDTO;
import com.example.core.jpa.repository.MenuPermissionRepository;
import com.example.core.jpa.repository.MenuRepository;
import com.example.core.jpa.entity.User;
import com.example.core.jpa.entity.UserGroup;
import com.example.core.jpa.repository.UserGroupRepository;
import com.example.core.jpa.repository.UserRepository;
import com.example.web.common.exceptions.GroupNotFoundException;
import com.example.web.common.exceptions.MenuNotFoundException;
import com.example.web.common.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MenuPermissionService {
	private final MenuPermissionRepository permissionRepository;
	private final UserRepository userRepository;
	private final UserGroupRepository groupRepository;
	private final MenuRepository menuRepository;

	/**
	 * 사용자의 메뉴 접근 권한을 조회합니다.
	 * 사용자 직접 권한이 있으면 해당 권한을 우선 적용하고,
	 * 없는 경우 사용자가 속한 그룹의 권한을 확인합니다.
	 */
	@Transactional(readOnly = true)
	public MenuAccessType getUserMenuAccess(Long userId, Long menuId) {
		// 사용자 직접 권한 확인
		Optional<MenuPermission> userPermission = permissionRepository
			.findByUserIdAndMenuId(userId, menuId);

		if (userPermission.isPresent()) {
			log.debug("User direct permission found for userId: {}, menuId: {}",
				userId, menuId);
			return userPermission.get().getAccessType();
		}

		// 사용자 그룹 권한 확인
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + userId));

		return user.getUserGroups().stream()
			.map(group -> permissionRepository
				.findByUserGroupIdAndMenuId(group.getId(), menuId))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(MenuPermission::getAccessType)
			.findFirst()
			.orElse(MenuAccessType.NO_ACCESS);
	}

	/**
	 * 사용자에게 메뉴 권한을 설정합니다.
	 */
	public void setUserMenuPermission(Long userId, Long menuId, MenuAccessType accessType) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + userId));
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다: " + menuId));

		Optional<MenuPermission> existingPermission =
			permissionRepository.findByUserIdAndMenuId(userId, menuId);

		if (existingPermission.isPresent()) {
			existingPermission.get().setAccessType(accessType);
			permissionRepository.save(existingPermission.get());
		} else {
			MenuPermission newPermission = MenuPermission.builder()
				.user(user)
				.menu(menu)
				.accessType(accessType)
				.build();
			permissionRepository.save(newPermission);
		}
	}

	/**
	 * 그룹에 메뉴 권한을 설정합니다.
	 */
	public void setGroupMenuPermission(Long groupId, Long menuId, MenuAccessType accessType) {
		UserGroup group = groupRepository.findById(groupId)
			.orElseThrow(() -> new GroupNotFoundException("그룹을 찾을 수 없습니다: " + groupId));
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다: " + menuId));

		Optional<MenuPermission> existingPermission =
			permissionRepository.findByUserGroupIdAndMenuId(groupId, menuId);

		if (existingPermission.isPresent()) {
			existingPermission.get().setAccessType(accessType);
			permissionRepository.save(existingPermission.get());
		} else {
			MenuPermission newPermission = MenuPermission.builder()
				.userGroup(group)
				.menu(menu)
				.accessType(accessType)
				.build();
			permissionRepository.save(newPermission);
		}
	}

	/**
	 * 특정 메뉴에 대한 모든 권한을 조회합니다.
	 */
	@Transactional(readOnly = true)
	public List<MenuPermissionDTO> getMenuPermissions(Long menuId) {
		List<MenuPermission> permissions = permissionRepository.findByMenuId(menuId);
		return permissions.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	/**
	 * 사용자의 모든 메뉴 권한을 조회합니다.
	 */
	@Transactional(readOnly = true)
	public List<MenuPermissionDTO> getUserPermissions(Long userId) {
		List<MenuPermission> permissions = permissionRepository.findByUserId(userId);
		return permissions.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	/**
	 * 그룹의 모든 메뉴 권한을 조회합니다.
	 */
	@Transactional(readOnly = true)
	public List<MenuPermissionDTO> getGroupPermissions(Long groupId) {
		List<MenuPermission> permissions = permissionRepository.findByUserGroupId(groupId);
		return permissions.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	/**
	 * 메뉴 권한을 삭제합니다.
	 */
	public void deletePermission(Long permissionId) {
		permissionRepository.deleteById(permissionId);
	}

	/**
	 * Entity를 DTO로 변환합니다.
	 */
	private MenuPermissionDTO convertToDTO(MenuPermission permission) {
		return MenuPermissionDTO.builder()
			.id(permission.getId())
			.menuId(permission.getMenu().getId())
			.userId(permission.getUser() != null ? permission.getUser().getId() : null)
			.userGroupId(permission.getUserGroup() != null ?
				permission.getUserGroup().getId() : null)
			.accessType(permission.getAccessType())
			.build();
	}
}
