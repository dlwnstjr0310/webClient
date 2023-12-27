package com.web.refactor.exception.student;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundStudentException extends StudentException {
	public NotFoundStudentException() {
		super(Error.NOT_FOUND_STUDENT, HttpStatus.NOT_FOUND);
	}
}
