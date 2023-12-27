package com.web.refactor.model.request;

import com.web.refactor.domain.common.CommonConstant;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.domain.entity.employee.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeRequest {

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "회원가입 요청 객체")
	public static class Join {

		@NotNull
		@Size(max = 50)
		@Pattern(regexp = CommonConstant.RegExp.EMAIL)
		String email;

		@NotNull
		@Size(min = 8, max = 20)
		String password;

		@NotNull
		String account;

		@NotNull
		String employeeName;

		@NotNull
		String phone;

		public Employee toEntity() {
			return Employee.builder()
					.email(email)
					.password(password)
					.account(account)
					.employeeName(employeeName)
					.phone(phone)
					.role(Role.ROLE_WAIT)
					.build();
		}
	}

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "로그인 요청 객체, 비밀번호 변경 객체")
	public static class Login {

		@NotNull
		String account;

		@NotNull
		@Size(min = 8, max = 20)
		String password;
	}

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "Token 재발급 요청 객체")
	public static class Token {

		@NotNull
		String refreshToken;
	}
}
