package com.example.web.common.exceptions;

/**
 * 그룹을 찾을 수 없을 때 발생하는 예외
 */
public class GroupNotFoundException extends BaseException {
	private static final String ERROR_CODE = "GROUP_NOT_FOUND";

	public GroupNotFoundException(String message) {
		super(message, ERROR_CODE);
	}

	public GroupNotFoundException(String message, Throwable cause) {
		super(message, ERROR_CODE, cause);
	}

	public GroupNotFoundException(Long groupId) {
		super(String.format("그룹을 찾을 수 없습니다. (ID: %d)", groupId), ERROR_CODE);
	}
}
