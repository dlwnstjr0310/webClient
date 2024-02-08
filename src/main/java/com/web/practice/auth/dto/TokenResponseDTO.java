package com.web.practice.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "로그인 응답 객체(JWT)")
public class TokenResponseDTO {

	String tokenType;

	String accessToken;

	Long expiresIn;

	String refreshToken;

	Long refreshTokenExpiresIn;

	Long empId;

	String empName;

	String empRole;

}
