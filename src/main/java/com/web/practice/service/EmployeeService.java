package com.web.practice.service;

import com.web.practice.auth.TokenProvider;
import com.web.practice.auth.dto.TokenResponseDTO;
import com.web.practice.domain.entity.employee.Employee;
import com.web.practice.exception.auth.login.AccountNotEnabledException;
import com.web.practice.exception.auth.login.InvalidPasswordException;
import com.web.practice.exception.employee.AlreadyExistAccountException;
import com.web.practice.exception.employee.AlreadyExistEmailException;
import com.web.practice.exception.employee.NotFoundEmployeeException;
import com.web.practice.model.request.EmployeeRequest;
import com.web.practice.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final TokenProvider tokenProvider;
	private final SendMailService sendMailService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final EmployeeRepository employeeRepository;

	@Transactional
	public void join(EmployeeRequest.Join request) {
		employeeRepository.findByEmail(request.getEmail()).ifPresent(member -> {
			throw new AlreadyExistEmailException();
		});

		employeeRepository.findByAccount(request.getAccount()).ifPresent(member -> {
			throw new AlreadyExistAccountException();
		});

		request.setPassword(passwordEncoder.encode(request.getPassword()));

		employeeRepository.save(request.toEntity());
	}

	@Transactional
	public TokenResponseDTO login(EmployeeRequest.Login request) {
		Employee employee = employeeRepository.findByAccount(request.getAccount())
				.orElseThrow(NotFoundEmployeeException::new);

		if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
			throw new InvalidPasswordException();
		}
		if (employee.isEnabled()) {
			throw new AccountNotEnabledException();
		}

		TokenResponseDTO tokenResponseDto = tokenProvider.generateTokenResponse(employee);

		employee.setRefreshToken(tokenResponseDto.getRefreshToken());
		employeeRepository.save(employee);

		return tokenResponseDto;
	}

	@Transactional(readOnly = true)
	public String reissueAccessToken(String token) {

		Employee employee = employeeRepository.findByRefreshToken(token)
				.orElseThrow(NotFoundEmployeeException::new);

		return tokenProvider.generateToken(employee);
	}

	@Transactional(readOnly = true)
	public String getLoginId(String email) {
		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow(NotFoundEmployeeException::new);

		return employee.getAccount();
	}

	@Transactional
	public void findMyPassword(String email, String account) {
		employeeRepository.findByEmailAndAccount(email, account)
				.ifPresent(sendMailService::createMailAndChangePassword);
	}

	public void modifiedPassword(EmployeeRequest.Login request) {
		Employee employee = employeeRepository.findByAccount(request.getAccount())
				.orElseThrow(NotFoundEmployeeException::new);

		employee.setPassword(passwordEncoder.encode(request.getPassword()));
		employeeRepository.save(employee);
	}

	public void logout(Employee employee) {
		employee.setRefreshToken(null);
		employeeRepository.save(employee);
	}
}
