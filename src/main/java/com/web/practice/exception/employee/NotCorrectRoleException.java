package com.web.practice.exception.employee;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class NotCorrectRoleException extends EmployeeException {
	public NotCorrectRoleException() {
		super(Error.NOT_CORRECT_ROLE, HttpStatus.BAD_REQUEST);
	}
}
