package com.web.refactor.service;

import com.web.refactor.domain.common.Status;
import com.web.refactor.domain.entity.employee.Employee;
import com.web.refactor.domain.entity.employee.Role;
import com.web.refactor.exception.employee.NotCorrectDepartmentException;
import com.web.refactor.exception.employee.NotCorrectRoleException;
import com.web.refactor.exception.employee.NotFoundEmployeeException;
import com.web.refactor.model.response.EmployeeResponse;
import com.web.refactor.repository.EmployeeRepository;
import com.web.refactor.repository.custom.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final EmployeeRepository employeeRepository;

	@Transactional(readOnly = true)
	public List<EmployeeResponse.Info> viewNewEmployeeList() {
		return EmployeeResponse.Info.of(employeeRepository.findAllByIsLockedIsTrue());
	}

	@Transactional(readOnly = true)
	public EmployeeResponse.Inventory viewEmployeeList(Pageable pageable, String department, String keyword) {

		return EmployeeResponse.Inventory.of(adminRepository.findAllByStatus(pageable, department, keyword));
	}

	@Transactional
	public void setUpAdditionalInfoForEmployee(Long id, String role, String department) {
		Employee employee = employeeRepository.findById(id).orElseThrow(NotFoundEmployeeException::new);

		Role roleType = switch (role) {
			case "TEACHER" -> Role.ROLE_TEACHER;
			case "MEMBER" -> Role.ROLE_MEMBER;
			case "ADMIN" -> Role.ROLE_ADMIN;
			default -> throw new NotCorrectRoleException();
		};

		Status.Department departmentType = switch (department) {
			case "ADMINISTRATION" -> Status.Department.ADMINISTRATION;
			case "MANAGEMENT" -> Status.Department.MANAGEMENT;
			case "INSTRUCTOR" -> Status.Department.INSTRUCTOR;
			default -> throw new NotCorrectDepartmentException();
		};

		employee.setUpAdditionalInfo(roleType, departmentType);

		employeeRepository.save(employee);
	}

	@Transactional
	public void removeEmployees(List<Long> idList) {

		List<Employee> employeeList = employeeRepository.findAllById(idList);

		if (employeeList.size() != idList.size()) {
			throw new NotFoundEmployeeException();
		}
		employeeRepository.deleteAllByIdInBatch(idList);
	}
}
