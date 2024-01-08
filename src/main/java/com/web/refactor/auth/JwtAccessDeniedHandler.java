package com.web.refactor.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.refactor.exception.Error;
import com.web.refactor.model.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		Response<Void> error = Response.<Void>builder()
				.code(Error.PERMISSION_DENIED.getCode())
				.message(Error.PERMISSION_DENIED.getMessage())
				.build();

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json; charset=UTF-8");

		String json = new ObjectMapper().writeValueAsString(error);

		response.getWriter().write(json);
		response.getWriter().flush();
	}
}