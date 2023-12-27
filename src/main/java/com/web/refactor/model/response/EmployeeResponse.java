package com.web.refactor.model.response;

import com.web.refactor.domain.entity.employee.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

@UtilityClass
public class EmployeeResponse {

	@Getter
	@Setter
	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "회원가입을 요청한 사용자 목록 조회용")
	public static class Info {

		Long id;

		String name;

		String phone;

		String email;

		String account;

		public static List<Info> of(List<Employee> employeeList) {
			return employeeList.stream().map(Info::of).toList();
		}

		public static Info of(Employee employee) {
			return Info.builder()
					.id(employee.getId())
					.name(employee.getEmployeeName())
					.phone(employee.getPhone())
					.email(employee.getEmail())
					.account(employee.getAccount())
					.build();
		}
	}

	@Getter
	@Setter
	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "사용자 목록 조회용")
	public static class Detail extends Info {

		String role;

		String workStatus;

		String department;

	}

	@Getter
	@Setter
	@Builder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@Schema(name = "사용자 목록 조회용")
	public static class Inventory {

		long totalCount;

		List<Detail> memberList;

		public static Inventory of(Page<Detail> memberList) {
			return Inventory.builder()
					.memberList(memberList.getContent())
					.totalCount(memberList.getTotalElements())
					.build();
		}
	}
}
