package com.web.refactor.exception.study;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundStudyException extends StudyException {
	public NotFoundStudyException() {
		super(Error.NOT_FOUND_STUDY, HttpStatus.NOT_FOUND);
	}
}
