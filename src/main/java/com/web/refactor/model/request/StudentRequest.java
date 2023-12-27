package com.web.refactor.model.request;

import com.web.refactor.domain.entity.Student;
import jakarta.validation.constraints.NotEmpty;

public class StudentRequest {

	public record Info(@NotEmpty(message = "이름을 입력해주세요.")
	                   String name,
	                   @NotEmpty(message = "전화번호를 입력해주세요.")
	                   String phone,
	                   String address) {
		public Student toEntity() {
			return Student.builder()
					.name(name)
					.phone(phone)
					.address(address)
					.build();
		}
	}

}
