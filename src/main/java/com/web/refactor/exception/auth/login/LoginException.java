package com.web.refactor.exception.auth.login;

import com.web.refactor.exception.Error;
import com.web.refactor.exception.auth.AuthException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginException extends AuthException implements Serializable {

	public LoginException(Error error, HttpStatus httpStatus) {
		super(error, httpStatus);
	}

}
