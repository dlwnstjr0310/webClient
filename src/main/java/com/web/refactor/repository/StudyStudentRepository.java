package com.web.refactor.repository;

import com.web.refactor.domain.entity.StudyStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyStudentRepository extends JpaRepository<StudyStudent, Long> {
}
