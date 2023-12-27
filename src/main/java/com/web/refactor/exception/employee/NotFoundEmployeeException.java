package com.web.refactor.exception.employee;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundEmployeeException extends EmployeeException {

	public NotFoundEmployeeException() {
		super(Error.NOT_FOUND_EMPLOYEE, HttpStatus.NOT_FOUND);
	}
}
