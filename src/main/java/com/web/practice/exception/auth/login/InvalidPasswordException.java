package com.web.practice.exception.auth.login;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends LoginException {

	public InvalidPasswordException() {
		super(Error.INVALID_PASSWORD, HttpStatus.UNAUTHORIZED);
	}
}
