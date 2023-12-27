package com.web.refactor.auth.dto;

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

	final String tokenType;

	final String accessToken;

	final Long expiresIn;

	final String refreshToken;

	final Long refreshTokenExpiresIn;

	final Long memberId;

	final String username;

	final String memberRole;

}
