package com.web.refactor.domain.entity;

import com.web.refactor.domain.common.BaseTimeEntity;
import com.web.refactor.domain.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Calendar extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String title;

	String content;

	String color;

	LocalDateTime startDate;

	LocalDateTime endDate;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	Employee employee;

}
