package com.web.practice.controller;

import com.web.practice.model.response.EmployeeResponse;
import com.web.practice.model.response.Response;
import com.web.practice.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "회원가입 요청 승인 및 권한 변경 등의 관리자 API")
public class AdminController {

	private final AdminService adminService;

	// 관리자 : 신규 과정 생성, 강의 강사 등록, 회원가입 요청 승인 및 거절, 회원 관리

	@GetMapping("/new-members")
	@Operation(summary = "회원가입 요청목록 조회", description = "회원가입을 요청한 사용자를 조회하는 API 입니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = EmployeeResponse.Info.class)))
	public Response<List<EmployeeResponse.Info>> viewNewEmployeeList() {

		return Response.<List<EmployeeResponse.Info>>builder()
				.data(adminService.viewNewEmployeeList())
				.build();
	}

	@GetMapping("/members")
	@Operation(summary = "직원 목록 조회", description = "직원 목록을 조회하는 API 입니다.")
	@Parameters(value = {
			@Parameter(name = "pageable", description = "페이지 정보 및 정렬 정보"),
			@Parameter(name = "department", description = "부서 정보"),
			@Parameter(name = "keyword", description = "검색어")
	})
	@ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = EmployeeResponse.Detail.class)))
	public Response<EmployeeResponse.Inventory> viewEmployeeList(@PageableDefault(size = 20) @SortDefault(sort = "employeeId", direction = Sort.Direction.ASC) Pageable pageable,
	                                                             @RequestParam(required = false) String department,
	                                                             @RequestParam(required = false) String keyword) {

		return Response.<EmployeeResponse.Inventory>builder()
				.data(adminService.viewEmployeeList(pageable, department, keyword))
				.build();
	}

	@PatchMapping("/{id}")
	@Operation(summary = "직원 권한 및 부서정보 수정", description = "직원의 추가정보를 입력하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "입력 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = """
					1. 올바른 권한을 선택해주세요.
					2. 올바른 부서를 선택해주세요.
					""", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<Void> setUpAdditionalInfoForEmployee(@PathVariable Long id,
	                                                     @RequestBody String role,
	                                                     @RequestBody String department) {

		adminService.setUpAdditionalInfoForEmployee(id, role, department);
		return Response.<Void>builder()
				.build();
	}

	@DeleteMapping
	@Operation(summary = "회원가입 거절 및 사용자 정보 삭제", description = "회원가입 거절 및 사용자를 탈퇴시키는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<Void> removeEmployees(@RequestBody List<Long> idList) {

		adminService.removeEmployees(idList);
		return Response.<Void>builder()
				.build();
	}
}
