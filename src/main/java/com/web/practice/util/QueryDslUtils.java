package com.web.practice.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.web.practice.exception.common.NotCorrectCategoryException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.web.practice.domain.entity.employee.QEmployee.employee;

public class QueryDslUtils {

	public static OrderSpecifier getSortedColumn(Order order, Path<?> parent, String fieldName) {
		Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
		return new OrderSpecifier(order, fieldPath);
	}

	public static List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

		List<OrderSpecifier> orders = new ArrayList<>();


		if (!pageable.getSort().isEmpty()) {

			for (Sort.Order order : pageable.getSort()) {

				Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

				switch (order.getProperty()) {
					case "employeeId" -> orders.add(getSortedColumn(direction, employee, "id"));
					case "employeeName" -> orders.add(getSortedColumn(direction, employee, "name"));
					case "account" -> orders.add(getSortedColumn(direction, employee, "account"));
					case "email" -> orders.add(getSortedColumn(direction, employee, "email"));
					default -> throw new NotCorrectCategoryException();
				}
			}
		}
		return orders;
	}
}
