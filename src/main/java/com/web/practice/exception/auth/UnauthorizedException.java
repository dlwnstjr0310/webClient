package com.web.practice.exception.auth;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

	private Error error;

}
