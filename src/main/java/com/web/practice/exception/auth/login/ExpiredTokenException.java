package com.web.practice.exception.auth.login;

import com.web.practice.exception.Error;
import com.web.practice.exception.auth.AuthException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpiredTokenException extends AuthException {

	@Getter
	final
	String token;

	public ExpiredTokenException(String token) {
		super(Error.EXPIRED_TOKEN, HttpStatus.UNAUTHORIZED);
		this.token = token;
	}
}

