package com.example.web.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원가입 요청을 위한 DTO (Data Transfer Object) 클래스
 * 클라이언트로부터 받은 회원가입 정보를 서버로 전달하는 역할을 합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
	/**
	 * 사용자 이름
	 * 비어있지 않아야 하며, 3자 이상 20자 이하여야 합니다.
	 */
	@NotBlank(message = "사용자 이름은 필수입니다.")
	@Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하여야 합니다.")
	private String username;

	/**
	 * 이메일 주소
	 * 유효한 이메일 형식이어야 하며, 최대 50자까지 허용됩니다.
	 */
	@NotBlank(message = "이메일은 필수입니다.")
	@Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	private String email;

	/**
	 * 비밀번호
	 * 비어있지 않아야 하며, 6자 이상 40자 이하여야 합니다.
	 * 비밀번호는 보안을 위해 toString 에 포함하지 않습니다.
	 */
	@ToString.Exclude
	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 6, max = 40, message = "비밀번호는 6자 이상 40자 이하여야 합니다.")
	private String password;


}
