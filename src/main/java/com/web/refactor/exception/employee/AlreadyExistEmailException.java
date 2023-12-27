package com.web.refactor.exception.employee;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class AlreadyExistEmailException extends EmployeeException {

	public AlreadyExistEmailException() {
		super(Error.ALREADY_EXIST_EMAIL, HttpStatus.CONFLICT);
	}
}
