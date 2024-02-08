package com.web.practice.exception.auth;

import com.web.practice.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthException extends RuntimeException implements Serializable {

	@Getter
	final
	Error error;

	@Getter
	final
	HttpStatus httpStatus;

	public AuthException(Error error, HttpStatus httpStatus) {
		this.error = error;
		this.httpStatus = httpStatus;
	}
}
