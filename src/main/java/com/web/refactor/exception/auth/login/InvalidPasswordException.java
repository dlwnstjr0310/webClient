package com.web.refactor.exception.auth.login;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends LoginException {

	public InvalidPasswordException() {
		super(Error.INVALID_PASSWORD, HttpStatus.UNAUTHORIZED);
	}
}
