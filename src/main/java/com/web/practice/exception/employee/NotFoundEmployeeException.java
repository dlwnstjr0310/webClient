package com.web.practice.exception.employee;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundEmployeeException extends EmployeeException {

	public NotFoundEmployeeException() {
		super(Error.NOT_FOUND_EMPLOYEE, HttpStatus.NOT_FOUND);
	}
}
