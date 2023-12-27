package com.web.refactor.exception.employee;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotCorrectRoleException extends EmployeeException {
	public NotCorrectRoleException() {
		super(Error.NOT_CORRECT_ROLE, HttpStatus.BAD_REQUEST);
	}
}
