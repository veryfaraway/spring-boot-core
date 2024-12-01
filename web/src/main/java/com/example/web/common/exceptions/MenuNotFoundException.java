package com.example.web.common.exceptions;

/**
 * 메뉴를 찾을 수 없을 때 발생하는 예외
 */
public class MenuNotFoundException extends BaseException {
	private static final String ERROR_CODE = "MENU_NOT_FOUND";

	public MenuNotFoundException(String message) {
		super(message, ERROR_CODE);
	}

	public MenuNotFoundException(String message, Throwable cause) {
		super(message, ERROR_CODE, cause);
	}

	public MenuNotFoundException(Long menuId) {
		super(String.format("메뉴를 찾을 수 없습니다. (ID: %d)", menuId), ERROR_CODE);
	}
}
