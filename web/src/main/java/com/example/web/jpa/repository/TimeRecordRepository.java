package com.example.web.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.jpa.entity.TimeRecord;

/**
 * TimeRecord 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리입니다.
 * Spring Data JPA가 자동으로 구현체를 생성합니다.
 */
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
	// 기본 CRUD 메서드는 JpaRepository 에서 제공됩니다.
}
