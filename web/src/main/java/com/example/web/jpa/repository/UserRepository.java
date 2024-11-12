package com.example.web.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.jpa.entity.User;

/**
 * JPA를 사용한 사용자 리포지토리 인터페이스
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
