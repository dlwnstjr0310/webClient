package com.web.refactor.domain.entity;

import com.web.refactor.domain.common.BaseTimeEntity;
import com.web.refactor.domain.entity.embedded.StudyStudentPK;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class StudyStudent extends BaseTimeEntity {

	@EmbeddedId
	StudyStudentPK id;

	@Schema(name = "등록 날짜")
	LocalDateTime enrollDate;

	@Builder.Default
	@ColumnDefault("false")
	@Schema(name = "교육 이수 여부")
	Boolean isCoursed = false;

}
