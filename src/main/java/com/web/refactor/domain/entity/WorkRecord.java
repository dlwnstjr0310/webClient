package com.web.refactor.domain.entity;

import com.web.refactor.domain.entity.embedded.WorkRecordPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkRecord {

	@EmbeddedId
	WorkRecordPK id;

	Integer workStartTime;

	Integer workEndTime;
}
