package com.web.practice.exception.employee;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class AlreadyExistEmailException extends EmployeeException {

	public AlreadyExistEmailException() {
		super(Error.ALREADY_EXIST_EMAIL, HttpStatus.CONFLICT);
	}
}
