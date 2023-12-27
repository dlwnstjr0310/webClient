package com.web.refactor.exception.auth;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

	private Error error;

}
