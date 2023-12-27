package com.web.refactor.exception.employee;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotCorrectDepartmentException extends EmployeeException {
	public NotCorrectDepartmentException() {
		super(Error.NOT_CORRECT_DEPARTMENT, HttpStatus.BAD_REQUEST);
	}
}
