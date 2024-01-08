package com.web.refactor.service;

import com.web.refactor.auth.CustomPasswordEncoder;
import com.web.refactor.auth.TokenProvider;
import com.web.refactor.auth.dto.TokenResponseDTO;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.exception.auth.login.AccountNotEnabledException;
import com.web.refactor.exception.auth.login.InvalidPasswordException;
import com.web.refactor.exception.employee.AlreadyExistAccountException;
import com.web.refactor.exception.employee.AlreadyExistEmailException;
import com.web.refactor.exception.employee.NotFoundEmployeeException;
import com.web.refactor.model.request.EmployeeRequest;
import com.web.refactor.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final TokenProvider tokenProvider;
	private final CustomPasswordEncoder customPasswordEncoder;

	private final SendMailService sendMailService;

	private final EmployeeRepository employeeRepository;

	@Transactional
	public void join(EmployeeRequest.Join request) {
		employeeRepository.findByEmail(request.getEmail()).ifPresent(member -> {
			throw new AlreadyExistEmailException();
		});

		employeeRepository.findByAccount(request.getAccount()).ifPresent(member -> {
			throw new AlreadyExistAccountException();
		});

		request.setPassword(customPasswordEncoder.encode(request.getPassword()));

		employeeRepository.save(request.toEntity());
	}

	@Transactional
	public TokenResponseDTO login(EmployeeRequest.Login request) {
		Employee employee = employeeRepository.findByAccount(request.getAccount())
				.orElseThrow(NotFoundEmployeeException::new);

		if (!customPasswordEncoder.matches(request.getPassword(), employee.getPassword())) {
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
	public void reissueAccessToken(Employee employee) {

		tokenProvider.generateToken(employee);
	}

	@Transactional(readOnly = true)
	public String getLoginId(String email) {
		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow(NotFoundEmployeeException::new);

		return employee.getAccount();
	}

	@Transactional
	public void findMyPassword(String email, String account) {
		Optional<Employee> member = employeeRepository.findByEmailAndAccount(email, account);

		member.ifPresent(sendMailService::createMailAndChangePassword);
	}

	public void modifiedPassword(EmployeeRequest.Login request) {
		Employee employee = employeeRepository.findByAccount(request.getAccount())
				.orElseThrow(NotFoundEmployeeException::new);

		employee.setPassword(customPasswordEncoder.encode(request.getPassword()));
		employeeRepository.save(employee);
	}

	public void logout(Employee employee) {
		employee.setRefreshToken(null);
		employeeRepository.save(employee);
	}
}
