package com.web.refactor.repository.custom;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.refactor.domain.common.Status;
import com.web.refactor.model.response.EmployeeResponse;
import com.web.refactor.util.QueryDslUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.web.refactor.domain.entity.employee.QEmployee.employee;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

	private final JPAQueryFactory queryFactory;

	public Page<EmployeeResponse.Detail> findAllByStatus(Pageable pageable, String department, String keyword) {

		List<OrderSpecifier> orders = QueryDslUtils.getAllOrderSpecifiers(pageable);

		BooleanExpression toEqualsExpression = null;

		if (keyword != null) {
			toEqualsExpression = employee.employeeName.eq(keyword);
		}

		JPAQuery<EmployeeResponse.Detail> memberList = queryFactory.select(
						Projections.bean(EmployeeResponse.Detail.class,
								employee.id.as("id"),
								employee.employeeName.as("name"),
								employee.phone.as("phone"),
								employee.email.as("email"),
								employee.account.as("account"),
								employee.role.stringValue().as("role"),
								employee.department.stringValue().as("department"))
				)
				.from(employee)
				.where(employee.department.eq(Status.Department.stringToSearchKeywordStatus(department)),
						toEqualsExpression)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.orderBy(
						orders.toArray(OrderSpecifier[]::new)
				);

		JPAQuery<Long> totalCount = queryFactory.select(employee.id.countDistinct())
				.where(employee.department.eq(Status.Department.stringToSearchKeywordStatus(department)),
						toEqualsExpression)
				.from(employee);

		return PageableExecutionUtils.getPage(memberList.fetch(), pageable, totalCount::fetchOne);
	}
}
