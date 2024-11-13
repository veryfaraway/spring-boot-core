package com.example.web.jpa.repository.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.web.jpa.entity.QUser;
import com.example.web.jpa.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * QueryDSL을 사용한 사용자 쿼리 리포지토리
 */
@Repository
public class UserQueryRepository {
	private final JPAQueryFactory queryFactory;

	public UserQueryRepository(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	/**
	 * 이름으로 사용자를 검색합니다.
	 */
	public List<User> findByName(String name) {
		QUser user = QUser.user;
		return queryFactory.selectFrom(user)
			.where(user.username.eq(name))
			.fetch();
	}
}
