package com.web.refactor.model.request;

import com.web.refactor.domain.entity.StudyStudent;
import com.web.refactor.domain.entity.embedded.StudyStudentPK;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudyStudentRequest {

	public record Info(
			@NotNull(message = "강의 ID 는 필수정보입니다.")
			Long studyId,
			@NotNull(message = "수강생 ID 는 필수정보입니다.")
			Long studentId,

			String enrollDate
	) {
		public StudyStudent toEntity() {
			return StudyStudent.builder()
					.id(StudyStudentPK.builder()
							.studyId(studyId)
							.studentId(studentId)
							.build())
					.enrollDate(LocalDate.parse(enrollDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
					.build();
		}
	}

}
