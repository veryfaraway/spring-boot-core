package com.example.core.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.core.jpa.entity.Menu;

/**
 * 메뉴 엔티티에 대한 데이터 접근 인터페이스
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	// 부모 메뉴가 없는(최상위) 메뉴 목록 조회
	List<Menu> findByParentIsNullOrderByDisplayOrder();

	// 특정 부모 메뉴의 하위 메뉴 목록 조회
	List<Menu> findByParentIdOrderByDisplayOrder(Long parentId);

	// 활성화된 메뉴만 조회
	@Query("SELECT m FROM Menu m WHERE m.active = true ORDER BY m.displayOrder")
	List<Menu> findAllActiveMenus();

	// URL로 메뉴 조회
	Optional<Menu> findByUrl(String url);

	// 특정 깊이의 메뉴 조회
	@Query(value = """
        WITH RECURSIVE MenuHierarchy AS (
            SELECT m.*, 0 as depth
            FROM menus m
            WHERE m.parent_id IS NULL
            
            UNION ALL
            
            SELECT m.*, h.depth + 1
            FROM menus m
            INNER JOIN MenuHierarchy h ON m.parent_id = h.id
        )
        SELECT * FROM MenuHierarchy WHERE depth = :depth
        ORDER BY display_order
        """, nativeQuery = true)
	List<Menu> findMenusByDepth(@Param("depth") int depth);
}

