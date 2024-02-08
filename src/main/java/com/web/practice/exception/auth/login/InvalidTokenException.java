package com.web.practice.exception.auth.login;

import com.web.practice.exception.Error;
import com.web.practice.exception.auth.AuthException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidTokenException extends AuthException {

	@Getter
	final
	String token;

	public InvalidTokenException(String token) {
		super(Error.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		this.token = token;
	}

}
