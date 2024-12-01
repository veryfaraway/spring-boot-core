package com.example.core.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.menu.MenuAccessType;
import com.example.core.jpa.entity.MenuPermission;

@Repository
public interface MenuPermissionRepository extends JpaRepository<MenuPermission, Long> {
	// 기존 메소드들
	Optional<MenuPermission> findByUserIdAndMenuId(Long userId, Long menuId);
	Optional<MenuPermission> findByUserGroupIdAndMenuId(Long groupId, Long menuId);
	List<MenuPermission> findByUserId(Long userId);
	List<MenuPermission> findByUserGroupId(Long groupId);
	List<MenuPermission> findByMenuId(Long menuId);
	List<MenuPermission> findByAccessType(MenuAccessType accessType);

	// 특정 메뉴의 모든 권한 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM MenuPermission mp WHERE mp.menu.id = :menuId")
	void deleteByMenuId(@Param("menuId") Long menuId);

	// 특정 사용자나 그룹의 권한 삭제
	@Modifying
	@Query("DELETE FROM MenuPermission mp WHERE " +
		"(mp.user.id = :userId OR mp.userGroup.id = :groupId) " +
		"AND mp.menu.id = :menuId")
	void deleteByUserOrGroupAndMenu(
		@Param("userId") Long userId,
		@Param("groupId") Long groupId,
		@Param("menuId") Long menuId
	);

	// 특정 메뉴의 모든 권한 존재 여부 확인
	@Query("SELECT COUNT(mp) > 0 FROM MenuPermission mp " +
		"WHERE mp.menu.id = :menuId AND mp.accessType = :accessType")
	boolean existsByMenuIdAndAccessType(
		@Param("menuId") Long menuId,
		@Param("accessType") MenuAccessType accessType
	);

	// 특정 사용자의 특정 메뉴에 대한 모든 권한 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM MenuPermission mp WHERE mp.user.id = :userId AND mp.menu.id = :menuId")
	void deleteByUserIdAndMenuId(
		@Param("userId") Long userId,
		@Param("menuId") Long menuId
	);

	// 특정 그룹의 특정 메뉴에 대한 모든 권한 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM MenuPermission mp WHERE mp.userGroup.id = :groupId AND mp.menu.id = :menuId")
	void deleteByUserGroupIdAndMenuId(
		@Param("groupId") Long groupId,
		@Param("menuId") Long menuId
	);

	// 특정 메뉴의 모든 권한을 특정 타입으로 업데이트
	@Modifying
	@Transactional
	@Query("UPDATE MenuPermission mp SET mp.accessType = :accessType WHERE mp.menu.id = :menuId")
	void updateAccessTypeByMenuId(
		@Param("menuId") Long menuId,
		@Param("accessType") MenuAccessType accessType
	);
}
