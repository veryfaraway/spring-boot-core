package com.example.web.common.exceptions;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * 에러 응답을 위한 DTO
 */
@Data
@Builder
public class ErrorResponse {
	private String errorCode;
	private String message;
	private LocalDateTime timestamp;
}
