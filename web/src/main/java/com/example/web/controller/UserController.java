package com.example.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.jpa.entity.User;
import com.example.web.service.UserService;

/**
 * 사용자 컨트롤러
 */
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * JPA를 사용하여 모든 사용자를 조회합니다.
	 */
	@GetMapping("/jpa")
	public List<User> getAllUsersJpa() {
		return userService.findAllUsersJpa();
	}

	/**
	 * MyBatis를 사용하여 모든 사용자를 조회합니다.
	 */
	@GetMapping("/mybatis")
	public List<User> getAllUsersMyBatis() {
		return userService.findAllUsersMyBatis();
	}

	/**
	 * QueryDSL을 사용하여 이름으로 사용자를 검색합니다.
	 */
	@GetMapping("/querydsl")
	public List<User> getUsersByNameQueryDsl(@RequestParam String name) {
		return userService.findUsersByNameQueryDsl(name);
	}

}
