package com.web.refactor.exception.employee;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class AlreadyExistAccountException extends EmployeeException {

	public AlreadyExistAccountException() {
		super(Error.ALREADY_EXIST_ACCOUNT, HttpStatus.CONFLICT);
	}
}
