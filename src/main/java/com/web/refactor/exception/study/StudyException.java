package com.web.refactor.exception.study;

import com.web.refactor.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudyException extends RuntimeException {

	Error error;

	HttpStatus httpStatus;

	public StudyException(Error error, HttpStatus httpStatus) {
		super(error.getMessage());
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
