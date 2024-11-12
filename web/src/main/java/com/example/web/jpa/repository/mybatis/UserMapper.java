package com.example.web.jpa.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.web.jpa.entity.User;

/**
 * MyBatis 를 사용한 사용자 매퍼 인터페이스
 */
@Mapper
public interface UserMapper {
	@Select("""
SELECT * FROM users
""")
	List<User> findAll();

	@Select("""
SELECT * FROM users WHERE id = #{id}
""")
	User findById(Long id);
}
