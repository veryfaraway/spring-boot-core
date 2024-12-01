package com.example.web.aop;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.web.common.exceptions.BaseException;
import com.example.web.common.exceptions.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * BaseException 하위 예외들을 처리합니다.
	 */
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
		log.error("BaseException occurred: {}", ex.getMessage(), ex);

		ErrorResponse response = ErrorResponse.builder()
			.errorCode(ex.getErrorCode())
			.message(ex.getMessage())
			.timestamp(LocalDateTime.now())
			.build();

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(response);
	}

	/**
	 * 예상치 못한 예외를 처리합니다.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

		ErrorResponse response = ErrorResponse.builder()
			.errorCode("INTERNAL_SERVER_ERROR")
			.message("내부 서버 오류가 발생했습니다.")
			.timestamp(LocalDateTime.now())
			.build();

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(response);
	}
}
