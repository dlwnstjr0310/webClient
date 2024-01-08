package com.web.refactor.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final TokenProvider tokenProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("*"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setMaxAge(3600L);
					return config;
				}))
				.authorizeHttpRequests(request -> request
						.requestMatchers(
								"/",
								"/swagger-ui/**",
								"/api-docs/**",
								"/auth/**"
						).permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN") //todo: 설정 다시해야함
						.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
						.anyRequest().authenticated()
				).exceptionHandling(exceptionHandling ->
						exceptionHandling
								.accessDeniedHandler(jwtAccessDeniedHandler)
								.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				)
				.apply(new JwtSecurityConfig(tokenProvider));

		return http.build();
	}

}
