package com.web.practice.auth;

import com.web.practice.auth.dto.TokenResponseDTO;
import com.web.practice.auth.service.CustomUserDetailsService;
import com.web.practice.domain.entity.employee.Employee;
import com.web.practice.exception.auth.UnauthorizedException;
import com.web.practice.exception.auth.login.ExpiredTokenException;
import com.web.practice.exception.auth.login.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private static final String TOKEN_TYPE = "Bearer";

	private static final String AUTHORITY_KEY = "auth";

	private static final long ACCESS_TOKEN_EXPIRE_TIME_MILLIS = 24L * 60L * 60L * 1000L;

	private static final long REFRESH_TOKEN_EXPIRE_TIME_MILLIS = 30L * 24L * 60L * 60L * 1000L;

	private final CustomUserDetailsService customUserDetailsService;

	@Value("${jwt.secret}")
	String secretKey;
	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public TokenResponseDTO generateTokenResponse(Employee employee) {

		long now = new Date().getTime();

		return TokenResponseDTO.builder()
				.empId(employee.getId())
				.empName(employee.getUsername())
				.empRole(employee.getRole().getProfile())
				.tokenType(TOKEN_TYPE)
				.accessToken(generateToken(employee))
				.expiresIn((now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS) / 1000)
				.refreshToken(generateRefreshToken(employee, new Date(now + REFRESH_TOKEN_EXPIRE_TIME_MILLIS)))
				.refreshTokenExpiresIn((now + REFRESH_TOKEN_EXPIRE_TIME_MILLIS) / 1000)
				.build();
	}

	public String generateToken(Employee employee) {

		long now = new Date().getTime();

		return Jwts.builder()
				.setIssuer("dev")
				.setSubject(employee.getId().toString())
				.claim(AUTHORITY_KEY, employee.getRole())
				.setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME_MILLIS))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	private String generateRefreshToken(Employee employee, Date expDate) {
		return Jwts.builder()
				.setSubject(employee.getId().toString())
				.setExpiration(expDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public Authentication getAuthentication(String token) {

		Claims claims = parseClaims(token);

		if (ObjectUtils.isEmpty(claims.get(AUTHORITY_KEY))) {
			throw new InvalidTokenException(token);
		}

		Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(claims.get(AUTHORITY_KEY).toString()));

		return new UsernamePasswordAuthenticationToken(
				customUserDetailsService.loadUserByUsername(claims.getSubject()),
				"",
				authorities
		);
	}

	public boolean isValidToken(String token) {
		parseClaims(token);
		return true;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException(token);
		} catch (Exception e) {
			throw new InvalidTokenException(token);
		}
	}

	public String getJwt(HttpServletRequest request) {
		String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.orElseThrow(UnauthorizedException::new);

		if (!StringUtils.hasText(token) || !token.startsWith(TOKEN_TYPE)) {
			throw new InvalidTokenException(token);
		}

		return token.substring(7);
	}
}
