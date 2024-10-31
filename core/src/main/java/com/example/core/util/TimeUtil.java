package com.example.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

/**
 * 시간 관련 유틸리티 클래스입니다.
 */
@UtilityClass
public class TimeUtil {
	/**
	 * 기본 날짜 시간 포맷
	 */
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 현재 시각을 DEFAULT_PATTERN 문자열로 반환합니다.
	 *
	 * @return 현재 시각 문자열 (형식: yyyy-MM-dd HH:mm:ss)
	 */
	public static String getCurrentTime() {
		return getCurrentTime(DEFAULT_PATTERN);
	}

	public static String getCurrentTime(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}


}
