package com.web.refactor.controller;

import com.web.refactor.model.request.StudentRequest;
import com.web.refactor.model.request.StudyStudentRequest;
import com.web.refactor.model.response.Response;
import com.web.refactor.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Tag(name = "Student", description = "수강생 관련 API")
public class StudentController {

	private final StudentService studentService;

	// 강사 : 출퇴근 등록, 내 강의의 수강생 출결 조회 및 관리, 출퇴근 기록 조회?
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "수강생 등록", description = "신규 수강생을 등록하는 API 입니다.")
	@ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	public Response<Void> inputStudent(@Valid @RequestBody StudentRequest.Info student) {

		studentService.inputStudent(student);
		return Response.<Void>builder()
				.build();
	}

	@PostMapping("/study")
	@PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'TEACHER')")
	@Operation(summary = "강의 별 수강생 등록", description = "강의의 수강생을 등록하는 API 입니다.")
	@ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	public Response<Void> inputStudyStudent(@Valid @RequestBody StudyStudentRequest.Info studyStudent) {

		studentService.inputStudyStudent(studyStudent);
		return Response.<Void>builder()
				.build();
	}
}
