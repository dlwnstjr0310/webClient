package com.web.practice.exception.employee;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class AlreadyExistAccountException extends EmployeeException {

	public AlreadyExistAccountException() {
		super(Error.ALREADY_EXIST_ACCOUNT, HttpStatus.CONFLICT);
	}
}
