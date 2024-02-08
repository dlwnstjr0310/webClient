package com.web.practice.exception.auth.login;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class AccountNotEnabledException extends LoginException {
	public AccountNotEnabledException() {
		super(Error.ACCOUNT_NOT_ENABLED, HttpStatus.FORBIDDEN);
	}
}
