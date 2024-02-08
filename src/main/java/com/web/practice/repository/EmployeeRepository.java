package com.web.practice.repository;

import com.web.practice.domain.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByAccount(String account);

	Optional<Employee> findByRefreshToken(String token);

	Optional<Employee> findByEmailAndAccount(String email, String account);

	List<Employee> findAllByIsLockedIsTrue();
}
