package com.web.refactor.controller;

import com.web.refactor.auth.dto.TokenResponseDTO;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.model.request.EmployeeRequest;
import com.web.refactor.model.response.Response;
import com.web.refactor.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Member", description = "회원가입, 로그인 및 사용자 서비스 API")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Operation(summary = "회원가입 요청", description = "사용자 회원가입 요청 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "회원가입 요청 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "409", description = """
					1.이미 존재하는 이메일 입니다.
					2.이미 존재하는 계정 입니다.
					""", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/join")
	public Response<Void> join(@Valid @RequestBody EmployeeRequest.Join request) {
		employeeService.join(request);

		return Response.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.build();
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "사용자 로그인 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = TokenResponseDTO.class))),
			@ApiResponse(responseCode = "401", description = """
					1. 비밀번호가 맞지 않습니다.
					2. 승인되지 않은 계정입니다.
					""", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<TokenResponseDTO> login(@Valid @RequestBody EmployeeRequest.Login request) {

		return Response.<TokenResponseDTO>builder()
				.data(employeeService.login(request))
				.build();
	}

	@PostMapping("/logout")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "로그아웃", description = "사용자 로그아웃 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(schema = @Schema(implementation = TokenResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<TokenResponseDTO> logout(@AuthenticationPrincipal Employee employee) {

		employeeService.logout(employee);
		return Response.<TokenResponseDTO>builder()
				.build();
	}

	@PostMapping("/token")
	@Operation(summary = "Token 재발급", description = "Login 시 제공된 RefreshToken 을 이용해 AccessToken 을 재발급 받는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "토큰 재발급 성공", content = @Content(schema = @Schema(implementation = TokenResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<String> reissueAccessToken(@Valid @RequestBody EmployeeRequest.Token request) {

		return Response.<String>builder()
				.data(employeeService.reissueAccessToken(request.getRefreshToken()))
				.build();
	}

	@PatchMapping("/password")
	@Operation(summary = "비밀번호 변경", description = "가입시 입력한 Email 과 아이디로 비밀번호 찾기를 진행하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "비밀번호 찾기 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<Void> modifiedPassword(@Valid @RequestBody EmployeeRequest.Login request) {

		employeeService.modifiedPassword(request);
		return Response.<Void>builder()
				.build();
	}

	@GetMapping("/login-id")
	@Operation(summary = "아이디 찾기", description = "가입시 입력한 Email 로 아이디 찾기를 진행하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "아이디 찾기 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<String> getLoginId(@NotNull @RequestParam String email) {

		return Response.<String>builder()
				.data(employeeService.getLoginId(email))
				.build();
	}

	@GetMapping("/password")
	@Operation(summary = "비밀번호 찾기", description = "가입시 입력한 Email 과 아이디로 임시 비밀번호를 발급하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "비밀번호 발급 및 Email 발송 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 사용자입니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	public Response<Void> findMyPassword(@NotNull @RequestParam String email,
	                                     @NotNull @RequestParam String loginId) {

		employeeService.findMyPassword(email, loginId);
		return Response.<Void>builder()
				.build();
	}
}
