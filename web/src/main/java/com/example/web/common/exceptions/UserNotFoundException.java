package com.example.web.common.exceptions;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외
 */
public class UserNotFoundException extends BaseException {
	private static final String ERROR_CODE = "USER_NOT_FOUND";

	public UserNotFoundException(String message) {
		super(message, ERROR_CODE);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, ERROR_CODE, cause);
	}

	public UserNotFoundException(Long userId) {
		super(String.format("사용자를 찾을 수 없습니다. (ID: %d)", userId), ERROR_CODE);
	}
}
