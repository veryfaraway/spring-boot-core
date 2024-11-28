package com.example.web.model.dto;

import lombok.Data;

@Data
public class OtpRequest {
	private String username;
	private String otpCode;
}
