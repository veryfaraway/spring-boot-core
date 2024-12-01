package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

// import com.example.core.util.TimeUtil;

@Slf4j
@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		// // 모델에 추가된 메뉴 데이터 로깅
		// List<MenuDTO> menus = (List<MenuDTO>) model.getAttribute("menus");
		// log.debug("Home page loaded with {} menus", menus != null ? menus.size() : 0);
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/otp")
	public String otp() {
		return "otp";
	}

	@GetMapping("/user")
	public String userPage(Model model) {
		model.addAttribute("message", "Welcome User!");
		return "user";
	}

	@GetMapping("/admin")
	public String adminPage(Model model) {
		model.addAttribute("message", "Welcome Admin!");
		return "admin";
	}

}
