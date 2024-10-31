package com.example.core.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	/**
	 * 엔티티 생성 시점을 저장합니다.
	 * Spring Data JPA가 자동으로 관리합니다.
	 */
	@CreatedDate
	private LocalDateTime createdAt;

	/**
	 * 엔티티 수정 시점을 저장합니다.
	 * 엔티티가 수정될 때마다 자동으로 업데이트됩니다.
	 */
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
