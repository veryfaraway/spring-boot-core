package com.example.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.web.jpa.entity.TimeRecord;
import com.example.web.jpa.repository.TimeRecordRepository;

import lombok.RequiredArgsConstructor;

/**
 * 시간 기록 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TimeRecordService {
	private final TimeRecordRepository timeRecordRepository;

	/**
	 * 새로운 시간 기록을 저장합니다.
	 *
	 * @param time 기록할 시간 문자열
	 * @param description 시간 기록에 대한 설명
	 * @return 저장된 TimeRecord 엔티티
	 */
	public TimeRecord saveTimeRecord(String time, String description) {
		TimeRecord record = TimeRecord.builder()
			.recordedTime(time)
			.description(description)
			.build();
		return timeRecordRepository.save(record);
	}

	/**
	 * 모든 시간 기록을 조회합니다.
	 *
	 * @return 저장된 모든 TimeRecord 엔티티 목록
	 */
	public List<TimeRecord> getAllTimeRecords() {
		return timeRecordRepository.findAll();
	}
}
