package com.web.practice.exception;

import com.web.practice.auth.TokenProvider;
import com.web.practice.exception.auth.UnauthorizedException;
import com.web.practice.exception.auth.login.ExpiredTokenException;
import com.web.practice.exception.auth.login.InvalidTokenException;
import com.web.practice.exception.auth.login.LoginException;
import com.web.practice.exception.employee.EmployeeException;
import com.web.practice.model.response.Response;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = {"com.web.refactoring.controller"})
public class CommonExceptionHandler {

	private final TokenProvider tokenProvider;

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<Response<Void>> userExceptionHandler(EmployeeException e) {
		return ResponseEntity.status(e.getHttpStatus())
				.body(Response.<Void>builder()
						.code(e.getError().code)
						.message(e.getError().getMessage())
						.build());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public Response<Void> unauthorizedExceptionHandler(UnauthorizedException e) {
		return createResponse(Error.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public Response<Void> accessDeniedExceptionHandler(HttpServletRequest httpServletRequest, AccessDeniedException e) {
		log.warn("[data error]: message: {}", e.getMessage());
		System.out.println("권한 : " + httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
		try {
			String jwtToken = tokenProvider.getJwt(httpServletRequest);
			tokenProvider.isValidToken(jwtToken);
		} catch (ExpiredJwtException ex) {
			return createResponse(Error.EXPIRED_TOKEN);
		} catch (ExpiredTokenException | InvalidTokenException ex) {
			return createResponse(ex.getError());
		} catch (UnauthorizedException ex) {
			return createResponse(Error.UNAUTHORIZED);
		} catch (Exception ignored) {
		}
		return createResponse(Error.PERMISSION_DENIED);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidTokenException.class)
	public Response<Void> invalidTokenExceptionHandler(InvalidTokenException e) {
		log.warn("[auth error]: message: {}", e.getMessage());
		return createResponse(e.getError());
	}


	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Response<Void>> loginExceptionHandler(LoginException e) {
		log.warn("message: {}", e.getMessage());

		Error authError = e.getError();

		return ResponseEntity.status(e.getHttpStatus())
				.body(Response.<Void>builder()
						.code(authError.getCode())
						.message(authError.getMessage())
						.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response<Void> validExceptionHandler(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		String errorMessage;
		try {
			errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
		} catch (NullPointerException exception) {
			errorMessage = Objects.requireNonNull(bindingResult.getGlobalError()).getDefaultMessage();
		}
		return Response.<Void>builder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message(String.valueOf(errorMessage))
				.build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<Void> exceptionHandler(Exception e) {
		return Response.<Void>builder()
				.code(Error.INTERNAL_SERVER_ERROR.getCode())
				.message(Error.INTERNAL_SERVER_ERROR.getMessage())
				.build();
	}

	private Response<Void> createResponse(Error authError) {
		return createResponse(authError.getCode(), authError.getMessage());
	}

	private Response<Void> createResponse(Integer code, String message) {
		return Response.<Void>builder()
				.code(code)
				.message(message)
				.build();
	}

}
