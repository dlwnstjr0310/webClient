package com.web.practice.domain.entity;

import com.web.practice.domain.common.BaseTimeEntity;
import com.web.practice.domain.entity.employee.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Study extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Schema(name = "정원")
	Long capacity;

	@Schema(name = "회차")
	Long count;

	LocalDateTime startDate;

	LocalDateTime endDate;

	@Schema(name = "총 수업일 수")
	Integer totalStudyDay;

	@Schema(name = "총 수업시간")
	Integer totalStudyHour;

	@Builder.Default
	@ColumnDefault("false")
	Boolean isDelete = false;

	@ManyToOne
	@JoinColumn(name = "training_id")
	Training training;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	Employee teacher;

	@ManyToOne
	@JoinColumn(name = "modifier_id")
	Employee modifier;
}
