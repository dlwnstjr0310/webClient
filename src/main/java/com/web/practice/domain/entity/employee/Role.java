package com.web.practice.domain.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

	ROLE_WAIT("회원가입 승인 대기중", "Level 0"),
	ROLE_TEACHER("강사", "Level 1"),
	ROLE_MEMBER("사원", "Level 2"),
	ROLE_ADMIN("관리자", "Level 3");

	private final String description;
	private final String profile;
}
