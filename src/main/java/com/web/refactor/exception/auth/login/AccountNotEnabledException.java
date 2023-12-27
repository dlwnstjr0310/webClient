package com.web.refactor.exception.auth.login;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class AccountNotEnabledException extends LoginException {
	public AccountNotEnabledException() {
		super(Error.ACCOUNT_NOT_ENABLED, HttpStatus.FORBIDDEN);
	}
}
