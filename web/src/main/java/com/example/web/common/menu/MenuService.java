package com.example.web.common.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.jpa.entity.Menu;
import com.example.core.menu.MenuAccessType;
import com.example.core.jpa.repository.MenuPermissionRepository;
import com.example.core.jpa.repository.MenuRepository;
import com.example.core.menu.MenuDTO;
import com.example.web.common.exceptions.MenuNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "menuCache")
@Transactional
@RequiredArgsConstructor
public class MenuService {
	private final MenuRepository menuRepository;
	private final MenuPermissionRepository menuPermissionRepository;
	private final MenuPermissionService menuPermissionService;

	/**
	 * 모든 메뉴 조회 (캐시 적용)
	 * 계층 구조로 반환됨
	 */
	@Cacheable(key = "'allMenus'")
	@Transactional(readOnly = true)
	public List<MenuDTO> getAllMenus() {
		log.debug("Fetching all menus from database");
		List<Menu> rootMenus = menuRepository.findByParentIsNullOrderByDisplayOrder();
		return rootMenus.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	/**
	 * 특정 사용자가 접근 가능한 메뉴 목록 조회
	 */
	@Cacheable(key = "'userMenus_' + #userId")
	@Transactional(readOnly = true)
	public List<MenuDTO> getAccessibleMenus(Long userId) {
		List<Menu> allMenus = menuRepository.findByParentIsNullOrderByDisplayOrder();
		return allMenus.stream()
			.map(menu -> convertToDTOWithAccess(menu, userId))
			.filter(dto -> dto.getAccessType() != MenuAccessType.NO_ACCESS)
			.collect(Collectors.toList());
	}

	/**
	 * 메뉴 저장
	 */
	@CacheEvict(allEntries = true)
	public MenuDTO saveMenu(MenuDTO menuDTO) {
		Menu menu = convertToEntity(menuDTO);

		// 부모 메뉴가 지정된 경우 존재 여부 확인
		if (menuDTO.getParentId() != null) {
			Menu parentMenu = menuRepository.findById(menuDTO.getParentId())
				.orElseThrow(() -> new MenuNotFoundException(menuDTO.getParentId()));
			menu.setParent(parentMenu);
		}

		Menu savedMenu = menuRepository.save(menu);
		log.info("Menu saved successfully: {}", savedMenu.getId());
		return convertToDTO(savedMenu);
	}

	/**
	 * 메뉴 수정
	 */
	@CacheEvict(allEntries = true)
	public MenuDTO updateMenu(Long menuId, MenuDTO menuDTO) {
		Menu existingMenu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException(menuId));

		updateMenuFields(existingMenu, menuDTO);
		Menu updatedMenu = menuRepository.save(existingMenu);
		log.info("Menu updated successfully: {}", menuId);
		return convertToDTO(updatedMenu);
	}

	/**
	 * 메뉴 삭제
	 */
	@CacheEvict(allEntries = true)
	public void deleteMenu(Long menuId) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException(menuId));

		// 하위 메뉴가 있는지 확인
		if (!menu.getChildren().isEmpty()) {
			throw new IllegalStateException(
				"하위 메뉴가 있는 메뉴는 삭제할 수 없습니다: " + menuId);
		}

		// 관련된 권한 정보도 함께 삭제
		menuPermissionRepository.deleteByMenuId(menuId);
		menuRepository.delete(menu);
		log.info("Menu deleted successfully: {}", menuId);
	}

	/**
	 * 메뉴 활성화/비활성화
	 */
	@CacheEvict(allEntries = true)
	public void toggleMenuActive(Long menuId, boolean active) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException(menuId));
		menu.setActive(active);
		menuRepository.save(menu);
		log.info("Menu {} status changed to: {}", menuId, active);
	}

	/**
	 * 메뉴 순서 변경
	 */
	@CacheEvict(allEntries = true)
	public void updateMenuOrder(Long menuId, int newOrder) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException(menuId));
		menu.setDisplayOrder(newOrder);
		menuRepository.save(menu);
		log.info("Menu order updated for menu: {}", menuId);
	}

	/**
	 * URL로 메뉴 조회
	 */
	@Cacheable(key = "'menuUrl_' + #url")
	@Transactional(readOnly = true)
	public MenuDTO getMenuByUrl(String url) {
		Menu menu = menuRepository.findByUrl(url)
			.orElseThrow(() -> new MenuNotFoundException("URL not found: " + url));
		return convertToDTO(menu);
	}

	/**
	 * ID로 메뉴 조회
	 */
	@Cacheable(key = "'menu_' + #menuId")
	@Transactional(readOnly = true)
	public MenuDTO getMenuById(Long menuId) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new MenuNotFoundException(menuId));
		return convertToDTO(menu);
	}

	/**
	 * Entity -> DTO 변환 (권한 정보 포함)
	 */
	private MenuDTO convertToDTOWithAccess(Menu menu, Long userId) {
		MenuDTO dto = convertToDTO(menu);
		MenuAccessType accessType =
			menuPermissionService.getUserMenuAccess(userId, menu.getId());
		dto.setAccessType(accessType);

		if (!menu.getChildren().isEmpty()) {
			List<MenuDTO> childDTOs = menu.getChildren().stream()
				.map(child -> convertToDTOWithAccess(child, userId))
				.filter(childDto -> childDto.getAccessType() != MenuAccessType.NO_ACCESS)
				.collect(Collectors.toList());
			dto.setChildren(childDTOs);
		}

		return dto;
	}

	/**
	 * Entity -> DTO 변환 (기본)
	 */
	private MenuDTO convertToDTO(Menu menu) {
		MenuDTO dto = MenuDTO.builder()
			.id(menu.getId())
			.name(menu.getName())
			.url(menu.getUrl())
			.displayOrder(menu.getDisplayOrder())
			.active(menu.isActive())
			.parentId(menu.getParent() != null ? menu.getParent().getId() : null)
			.build();

		if (!menu.getChildren().isEmpty()) {
			List<MenuDTO> childDTOs = menu.getChildren().stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
			dto.setChildren(childDTOs);
		}

		return dto;
	}

	/**
	 * DTO -> Entity 변환
	 */
	private Menu convertToEntity(MenuDTO dto) {
		return Menu.builder()
			.id(dto.getId())
			.name(dto.getName())
			.url(dto.getUrl())
			.displayOrder(dto.getDisplayOrder())
			.active(dto.isActive())
			.build();
	}

	/**
	 * 메뉴 필드 업데이트
	 */
	private void updateMenuFields(Menu menu, MenuDTO dto) {
		menu.setName(dto.getName());
		menu.setUrl(dto.getUrl());
		menu.setDisplayOrder(dto.getDisplayOrder());
		menu.setActive(dto.isActive());

		if (dto.getParentId() != null &&
			!dto.getParentId().equals(menu.getParent() != null ?
				menu.getParent().getId() : null)) {
			Menu parentMenu = menuRepository.findById(dto.getParentId())
				.orElseThrow(() -> new MenuNotFoundException(dto.getParentId()));
			menu.setParent(parentMenu);
		}
	}
}
