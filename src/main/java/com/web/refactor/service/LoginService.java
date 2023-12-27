package com.web.refactor.service;

import com.web.refactor.exception.employee.NotFoundEmployeeException;
import com.web.refactor.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return employeeRepository.findById(Long.valueOf(username)).orElseThrow(NotFoundEmployeeException::new);
	}
}
