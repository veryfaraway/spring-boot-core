package com.example.web.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 로그인 요청을 위한 DTO (Data Transfer Object) 클래스
 * 클라이언트로부터 받은 로그인 정보를 서버로 전달하는 역할을 합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	/**
	 * 사용자 이름 (아이디)
	 * 비어있지 않아야 합니다.
	 */
	@NotBlank(message = "사용자 이름은 필수입니다.")
	private String username;

	/**
	 * 비밀번호
	 * 비어있지 않아야 합니다.
	 * 비밀번호는 보안을 위해 ToString 에 포함하지 않습니다.
	 */
	@ToString.Exclude
	@NotBlank(message = "비밀번호는 필수입니다.")
	private String password;
}
