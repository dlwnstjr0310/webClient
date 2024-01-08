package com.web.refactor.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.refactor.exception.Error;
import com.web.refactor.model.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		Response<Void> error = Response.<Void>builder()
				.code(Error.UNAUTHORIZED.getCode())
				.message(Error.UNAUTHORIZED.getMessage())
				.build();

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json; charset=UTF-8");

		String json = new ObjectMapper().writeValueAsString(error);

		response.getWriter().write(json);
		response.getWriter().flush();
	}
}
