package com.web.refactor.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* 1 : 회원
 * 2 : 과정
 * 3 : 강의
 * 4 : 수강생
 * 5 : 강의 별 수강생
 * 6 : 캘린더 */

@Getter
@AllArgsConstructor
public enum Error {

	NOT_CORRECT_CATEGORY(1000, "올바른 구분을 선택해주세요."),

	NOT_FOUND_EMPLOYEE(1102, "존재하지 않는 사용자입니다."),

	ALREADY_EXIST_EMAIL(1201, "이미 존재하는 이메일 입니다."),
	ALREADY_EXIST_ACCOUNT(1202, "이미 존재하는 계정 입니다."),
	NOT_CORRECT_ROLE(1301, "올바른 권한을 선택해주세요."),
	NOT_CORRECT_DEPARTMENT(1302, "올바른 부서를 선택해주세요."),

	NOT_FOUND_TRAINING(2001, "존재하지 않는 과정입니다."),

	NOT_FOUND_STUDY(3001, "존재하지 않는 강의입니다."),

	NOT_FOUND_STUDENT(4001, "존재하지 않는 수강생입니다."),

	NOT_FOUND_STUDY_STUDENT(5001, "존재하지 않는 강의 별 수강생입니다."),

	NOT_FOUND_CALENDAR(6001, "존재하지 않는 캘린더입니다."),

	EXPIRED_TOKEN(9000, "토큰이 만료되었습니다."),
	INVALID_TOKEN(9001, "잘못된 토큰입니다."),
	UNAUTHORIZED(9002, "로그인이 필요합니다."),
	PERMISSION_DENIED(9003, "권한이 없습니다."),
	INVALID_PASSWORD(9004, "비밀번호가 맞지 않습니다."),
	ACCOUNT_NOT_ENABLED(9005, "활성화 되지 않은 계정입니다."),

	INTERNAL_SERVER_ERROR(9999, "internal server error");

	final Integer code;

	final String message;
}
