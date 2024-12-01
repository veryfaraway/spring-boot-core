package com.example.web.common.menu;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.menu.MenuDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;
	private final MenuPermissionService menuPermissionService;

	// 메뉴 목록 조회
	@GetMapping
	public String getMenuList(Model model,
		@AuthenticationPrincipal UserDetails userDetails) {
		// 현재 사용자의 권한에 맞는 메뉴 목록 조회
		List<MenuDTO> menus = menuService.getAllMenus();
		model.addAttribute("menus", menus);
		return "menu/menuList";
	}

	// 메뉴 추가/수정 페이지
	@GetMapping("/edit")
	@PreAuthorize("hasPermission(#menuId, 'MENU', 'FULL_ACCESS')")
	public String editMenu(@RequestParam(required = false) Long menuId,
		Model model) {
		MenuDTO menu = menuId != null ?
			menuService.getMenuById(menuId) : new MenuDTO();
		model.addAttribute("menu", menu);
		return "menu/editMenu";
	}

	// 메뉴 저장
	@PostMapping("/save")
	@PreAuthorize("hasPermission(#menuDTO.id, 'MENU', 'FULL_ACCESS')")
	public String saveMenu(@Valid @ModelAttribute MenuDTO menuDTO,
		BindingResult result) {
		if (result.hasErrors()) {
			return "menu/editMenu";
		}
		menuService.saveMenu(menuDTO);
		return "redirect:/menu";
	}
}
