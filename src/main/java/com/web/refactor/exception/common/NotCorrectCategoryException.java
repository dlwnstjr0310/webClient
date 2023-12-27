package com.web.refactor.exception.common;

import com.web.refactor.exception.Error;
import org.springframework.http.HttpStatus;

public class NotCorrectCategoryException extends CommonException {
	public NotCorrectCategoryException() {
		super(Error.NOT_CORRECT_CATEGORY, HttpStatus.BAD_REQUEST);
	}
}
