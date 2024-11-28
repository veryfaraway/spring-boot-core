package com.example.core.util;

/**
 * OTP 검증을 위한 유틸리티 클래스
 */
public class OtpValidator {
	/**
	 * OTP 코드의 유효성을 검사합니다.
	 * @param inputOtp 사용자가 입력한 OTP 코드
	 * @param storedOtp 저장된 OTP 코드
	 * @return OTP 코드의 일치 여부
	 */
	public static boolean validateOtp(String inputOtp, String storedOtp) {
		return inputOtp != null && inputOtp.equals(storedOtp);
	}
}
