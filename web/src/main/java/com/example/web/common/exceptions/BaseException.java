package com.example.web.common.exceptions;

import lombok.Getter;

/**
 * 애플리케이션의 기본 예외 클래스
 * 모든 커스텀 예외의 부모 클래스
 */
@Getter
public abstract class BaseException extends RuntimeException {
	private final String errorCode;

	protected BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	protected BaseException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
}





