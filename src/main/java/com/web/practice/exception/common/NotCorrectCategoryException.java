package com.web.practice.exception.common;

import com.web.practice.exception.Error;
import org.springframework.http.HttpStatus;

public class NotCorrectCategoryException extends CommonException {
	public NotCorrectCategoryException() {
		super(Error.NOT_CORRECT_CATEGORY, HttpStatus.BAD_REQUEST);
	}
}
