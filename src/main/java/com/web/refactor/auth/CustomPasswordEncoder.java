package com.web.refactor.auth;

import com.web.refactor.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
@Component("passwordEncoder")
public class CustomPasswordEncoder implements PasswordEncoder {

	private final Pbkdf2Properties pbkdf2Properties;

	@Override
	public String encode(CharSequence rawPassword) {
		return BCrypt.hashpw(getCipherText(rawPassword), BCrypt.gensalt());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return BCrypt.checkpw(getCipherText(rawPassword), encodedPassword.replace("$2y$", "$2a$"));
	}

	@SneakyThrows({NoSuchAlgorithmException.class, InvalidKeySpecException.class})
	private String getCipherText(CharSequence rawPassword) {
		return AuthUtils.pbkdf2(
				rawPassword.toString(),
				pbkdf2Properties.getSalt(),
				pbkdf2Properties.getIterationCount(),
				pbkdf2Properties.getKeyLength()
		);
	}
}
