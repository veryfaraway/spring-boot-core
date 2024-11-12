package com.example.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.web.jpa.entity.User;
import com.example.web.jpa.repository.UserRepository;
import com.example.web.jpa.repository.mybatis.UserMapper;
import com.example.web.jpa.repository.querydsl.UserQueryRepository;

/**
 * 사용자 서비스 클래스
 */
@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserQueryRepository userQueryRepository;

	public UserService(UserRepository userRepository, UserMapper userMapper, UserQueryRepository userQueryRepository) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.userQueryRepository = userQueryRepository;
	}

	/**
	 * JPA를 사용하여 모든 사용자를 조회합니다.
	 */
	public List<User> findAllUsersJpa() {
		return userRepository.findAll();
	}

	/**
	 * MyBatis를 사용하여 모든 사용자를 조회합니다.
	 */
	public List<User> findAllUsersMyBatis() {
		return userMapper.findAll();
	}

	/**
	 * QueryDSL을 사용하여 이름으로 사용자를 검색합니다.
	 */
	public List<User> findUsersByNameQueryDsl(String name) {
		return userQueryRepository.findByName(name);
	}
}
