package com.example.web.jpa.entity;

import com.example.core.jpa.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 시간 기록을 저장하는 엔티티 클래스입니다.
 * 각각의 시간 기록에 대한 정보를 저장합니다.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_records")
public class TimeRecord extends BaseEntity {
	/**
	 * 기본 키로 사용되는 ID 필드입니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 기록된 시간 정보를 저장합니다.
	 * 형식: "yyyy-MM-dd HH:mm:ss"
	 */
	private String recordedTime;

	/**
	 * 시간 기록에 대한 설명을 저장합니다.
	 */
	private String description;
}
