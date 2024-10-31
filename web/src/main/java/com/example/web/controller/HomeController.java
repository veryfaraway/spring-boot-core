package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.core.util.TimeUtil;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model) {
		// model.addAttribute("message", "Welcome to the Spring Boot Application!");
		model.addAttribute("currentTime", TimeUtil.getCurrentTime());
		return "home";
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

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
