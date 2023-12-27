package com.web.refactor.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.refactor.domain.common.BaseTimeEntity;
import com.web.refactor.domain.entity.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Training extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Size(max = 100)
	String title;

	@Builder.Default
	@ColumnDefault("false")
	Boolean isDelete = false;

	@ManyToOne
	@JoinColumn(name = "writer")
	Employee writer;

	@ManyToOne
	@JoinColumn(name = "modifier")
	Employee modifier;

	@JsonIgnore
	@OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
	private List<Study> studyList;

}
