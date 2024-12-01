package com.example.core.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.core.jpa.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

	Optional<UserGroup> findByName(String name);

	// 특정 사용자가 속한 그룹 목록 조회
	@Query("SELECT g FROM UserGroup g JOIN g.users u WHERE u.id = :userId")
	List<UserGroup> findByUserId(@Param("userId") Long userId);

	// 특정 메뉴에 대한 권한이 있는 그룹 목록 조회
	@Query("SELECT DISTINCT g FROM UserGroup g " +
		"JOIN g.menuPermissions mp " +
		"WHERE mp.menu.id = :menuId")
	List<UserGroup> findByMenuId(@Param("menuId") Long menuId);
}
