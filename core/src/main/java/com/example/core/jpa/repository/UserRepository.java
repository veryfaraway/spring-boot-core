package com.example.core.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.core.jpa.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	// 특정 그룹에 속한 사용자 목록 조회
	@Query("SELECT u FROM User u JOIN u.userGroups g WHERE g.id = :groupId")
	List<User> findByUserGroupId(@Param("groupId") Long groupId);

	// 특정 메뉴에 대한 권한이 있는 사용자 목록 조회
	@Query("SELECT DISTINCT u FROM User u " +
		"JOIN u.menuPermissions mp " +
		"WHERE mp.menu.id = :menuId")
	List<User> findByMenuId(@Param("menuId") Long menuId);
}
