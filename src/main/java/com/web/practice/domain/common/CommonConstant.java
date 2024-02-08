package com.web.practice.domain.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstant {

	@UtilityClass
	public static class RegExp {
		public static final String EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

		public static final String PHONE = "^(01(?:0|1|[6-9])|0[2-9]\\d{1,2})[-.]?\\d{3,4}[-.]?\\d{4}$";

	}
}
