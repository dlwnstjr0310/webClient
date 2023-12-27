package com.web.refactor.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Status {

	@Getter
	@AllArgsConstructor
	public enum Department {

		ADMINISTRATION("행정", "DE0001"),
		MANAGEMENT("관리", "DE0002"),
		INSTRUCTOR("강사", "DE0003");

		private static final Map<String, String> STYLE_MAP = Collections.unmodifiableMap(
				Arrays.stream(values()).collect(Collectors.toMap(Department::getDescription, Department::name))
		);
		private final String description;
		private final String profile;

		public static Department stringToSearchKeywordStatus(String status) {
			String name = STYLE_MAP.get(status);
			if (name == null) {
				throw new IllegalArgumentException();
			}
			return Department.valueOf(name);
		}
	}
}
