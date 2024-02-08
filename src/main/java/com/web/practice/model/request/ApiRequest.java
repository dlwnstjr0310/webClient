package com.web.practice.model.request;

import com.web.practice.domain.entity.Study;
import com.web.practice.domain.entity.Training;
import com.web.practice.domain.entity.employee.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@UtilityClass
public class ApiRequest {

	@Getter
	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 강의 세부정보 검색용 객체")
	public static class Keyword {
		String id;
		Integer count;
		String centerId;
	}

	@Getter
	@Setter
	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 과정정보")
	public static class TrainingResult extends Keyword {

		String title;

		Map<Integer, StudyResult> studyResultMap;

		public Training toEntity(Employee writer) {
			return Training.builder()
					.title(title)
					.writer(writer)
					.studyList(new ArrayList<>())
					.build();
		}
	}

	@Getter
	@Setter
	@Builder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "HRD-NET API 강의 세부정보")
	public static class StudyResult {

		Integer count; // 3  : trprDegr

		Long capacity;  // 3 : totFxnum

		Integer totalStudyDay;   // 2 : trDcnt

		Integer totalStudyHour;  // 2 : trtm

		String startDate;   // 3 : trStaDt

		String endDate; // 3 : trEndDt

		String name; // 2 : trprChap

		String email; // 2 : trprChapEmail

		String telephone; // 2 : trprChapTel

		public Study toEntity(Training training) {
			return Study.builder()
					.count(Long.valueOf(count))
					.capacity(capacity)
					.totalStudyDay(totalStudyDay)
					.totalStudyHour(totalStudyHour)
					.startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
					.endDate(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
					.training(training)
					.build();
		}
	}
}
