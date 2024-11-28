package com.example.web.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.jpa.entity.User;

/**
 * JPA를 사용한 사용자 리포지토리 인터페이스
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * 사용자 이름으로 사용자 조회
	 *
	 * @param username 사용자 이름
	 * @return 조회된 사용자 (Optional)
	 */
	Optional<User> findByUsername(String username);

}
